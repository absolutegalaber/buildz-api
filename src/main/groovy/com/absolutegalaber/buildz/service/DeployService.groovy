package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.Deploy
import com.absolutegalaber.buildz.domain.DeployLabel
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import com.absolutegalaber.buildz.repository.BuildRepository
import com.absolutegalaber.buildz.repository.DeployRepository
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

    DeployService(
            BuildRepository buildRepository,
            ServerService serverService,
            DeployRepository deployRepository
    ) {
        this.buildRepository = buildRepository
        this.serverService = serverService
        this.deployRepository = deployRepository
    }

    /**
     * Find a list of Deploys via a Server name.
     *
     * @param serverName the name of a server
     * @return a list of views which represent Deploys on a Server
     * @throws InvalidRequestException  when the provided Server name is not associated to a Server
     */
    List<Deploy> byServer(String serverName) throws InvalidRequestException {
        // TODO would it be better to use a join in the deploysOnServer repo call?
        Server server = serverService.byName(serverName)
                .orElseThrow({ ->
                    new InvalidRequestException("No server found with name=${serverName}")
                })
        deployRepository.findAll(
                deploysOnServer(server),
                Sort.by('id').descending()
        )
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

        deployRepository.save(deploy)
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
