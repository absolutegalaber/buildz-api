package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.api.model.IArtifact
import com.absolutegalaber.buildz.api.model.IEnvironment
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.Deploy
import com.absolutegalaber.buildz.domain.DeployLabel
import com.absolutegalaber.buildz.domain.DeploySearch
import com.absolutegalaber.buildz.domain.DeploySearchResult
import com.absolutegalaber.buildz.domain.Deploy_
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.FutureDateException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import com.absolutegalaber.buildz.events.ReserveServerEvent
import com.absolutegalaber.buildz.repository.BuildRepository
import com.absolutegalaber.buildz.repository.DeployRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.*

/**
 * A Service which handles all business logic related to Deploys.
 */
@Service
@Transactional
class DeployService {

    private final BuildRepository buildRepository
    private final ServerService serverService
    private final DeployRepository deployRepository
    private final EnvironmentService environmentService

    DeployService(
            BuildRepository buildRepository,
            ServerService serverService,
            DeployRepository deployRepository,
            EnvironmentService environmentService
    ) {
        this.buildRepository = buildRepository
        this.serverService = serverService
        this.deployRepository = deployRepository
        this.environmentService = environmentService
    }

    /**
     * Search for Deploys based on parameters provided
     *
     * @param search parameters for filtering deploys
     * @return a search result that includes a page
     * of deploys
     */
    DeploySearchResult search(DeploySearch search) {
        DeploySearchResult.fromPageResult(
                deployRepository.findAll(deploysOnServer(search.serverName), search.page())
        )
    }

    /**
     * Validate the parameters and then attempt to find the Deploy that
     * was/is deployed on a Server at a specific Datetime.
     *
     * @param serverName the name of the server on which the Deploy was/is
     * @param deployedAt the Datetime which will be used to find the Deploy
     * on the server at the Datetime
     * @return A search result that includes a single Deploy
     * @throws DataNotFoundException the server was not found, or there was
     * no Deploy on the server at the provided Datetime
     * @throws InvalidRequestException the serverName or deployedAt Datetime
     * is missing or invalid
     * @throws FutureDateException the provided deployedAt Datetime is in the
     * future
     */
    DeploySearchResult onServerAt(String serverName, Date deployedAt)
            throws DataNotFoundException, InvalidRequestException, FutureDateException
    {
        if (serverName == null || serverName.trim() == "") {
            throw new InvalidRequestException("Could not find a Deploy - No Server Name provided")
        }

        if (deployedAt == null) {
            throw new InvalidRequestException("Could not find Deploy on ${serverName} - No Date provided")
        }

        if (deployedAt.after(new Date())) {
            throw new FutureDateException("Could not return Deploy on ${serverName} - Provided Date is in the future")
        }

        Page<Deploy> deploys = deployRepository.findAll(
                deployOnServerAt(serverName, deployedAt),
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, Deploy_.DEPLOYED_AT))
        )

        if (deploys == null || deploys.getContent() == null || deploys.getContent().size() <= 0) {
            throw new DataNotFoundException("No Deploy found on " + serverName + " at " + deployedAt)
        }

        DeploySearchResult.fromPageResult(deploys)
    }

    /**
     * Find a Deploy via its ID and return all data that is relevant to the user.
     *
     * @param id the ID of a Deploy saved in the Buildz System
     * @return a view object that contains all relevant Deploy data
     * @throws DataNotFoundException    The provided ID is not associated to a Deploy
     */
    Deploy byId(Long id) throws DataNotFoundException {
        deployRepository.findOne(deployWithId(id)).orElseThrow(
                { -> new DataNotFoundException("No Deploy found for deployId=" + id) }
        )
    }

    /**
     * Registers a Deploy, of a specific Build, which is associated to a
     * specific Server in the Buildz System.
     *
     * If the provided serverName is not associated to a Server in Buildz
     * System, a Server with that name will be generated and saved.
     *
     * @param event an event object that contains all necessary data to register a Deploy
     * @return all relevant information to the recently registered Deploy
     * @throws InvalidRequestException  when the provided params are invalid
     * @throws DataNotFoundException    when the the provided project, branch,
     *                                  and/or buildNumber are not associated
     *                                  to an existing Build
     */
    Deploy register(RegisterDeployEvent event) throws InvalidRequestException, DataNotFoundException {
        Build build = buildRepository.findOne(buildWithProjectBranchAndNumber(
                event.getProject(), event.getBranch(), event.getBuildNumber()
        ))
                .orElseThrow({ ->
                    new DataNotFoundException(
                            "No Build found for project='${event.getProject()}'," +
                                    " branch='${event.getBranch()}'," +
                                    " buildNumber='${event.getBuildNumber()}'"
                    )
                })

        Server server = serverService.trackServer(event.getServerName())

        Deploy deploy = new Deploy(
                deployedAt: new Date(),
                build: build,
                server: server
        )

        if (event.getLabels()) {
            // TODO add additional checks (e.g. key == null || key == '')
            event.getLabels().forEach({ key, value ->
                deploy.getLabels().add(new DeployLabel(
                        key: key,
                        value: value,
                        deploy: deploy
                ))
            })
        }

        Deploy savedDeploy = deployRepository.save(deploy)

        if (event.getReservedBy()?.trim()) {
            serverService.reserveServer(
                    server,
                    new ReserveServerEvent(
                            reservedBy: event.getReservedBy(),
                            reservationNote: event.getReservationNote()
                    )
            )
        }

        Optional<Environment> environment =
                environmentService.byName(Environment.generateInternalName(server.name))

        IEnvironment toSave
        if (environment.isPresent()) {
            toSave = IEnvironment.of(environment.get())

            IArtifact artifact = toSave.getArtifacts().find {
                it.project == event.project && it.branch == event.branch
            }
            if (artifact == null) {
                toSave.getArtifacts().add(new IArtifact(
                        project: event.project,
                        branch: event.branch,
                        buildNumber: event.buildNumber
                ))
            } else {
                // First remove the current Artifact...
                toSave.getArtifacts().remove(artifact)
                // then re-add the Artifact with an updated BuildNumber
                toSave.getArtifacts().add(new IArtifact(
                        project: event.project,
                        branch: event.branch,
                        buildNumber: event.buildNumber
                ))
            }
        } else {
            toSave = new IEnvironment()

            toSave.setName(Environment.generateInternalName(server.name))
            toSave.setArtifacts([new IArtifact(project: event.project, branch: event.branch)])
        }

        environmentService.save(toSave, true)

        savedDeploy
    }

    /**
     * Adds Labels to the Deploy with the provided ID.
     *
     * @param id the ID of the Deploy to which the labels should be added to
     * @param labels the Labels that should be added
     * @return the Deploy that was just updated with the provided labels
     * @throws DataNotFoundException    When the provided Deploy ID is not
     *                                  registered in the Buildz System
     */
    Deploy addLabels(Long id, Map<String, String> labels) throws DataNotFoundException {
        Deploy deploy = deployRepository.findOne(deployWithId(id))
                .orElseThrow({ -> new DataNotFoundException("No Build found for id'${id}'") })

        if (labels) {
            // TODO add additional checks (e.g. key == null || key == '')
            labels.forEach({ key, value ->
                deploy.getLabels().add(new DeployLabel(
                        key: key,
                        value: value,
                        deploy: deploy
                ))
            })
        }


        deployRepository.save(deploy)
    }
}
