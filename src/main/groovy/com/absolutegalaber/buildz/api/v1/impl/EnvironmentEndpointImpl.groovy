package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.model.IArtifact
import com.absolutegalaber.buildz.api.model.IEnvironment
import com.absolutegalaber.buildz.api.v1.EnvironmentEndpoint
import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.EnvironmentService
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional

@RestController
@Transactional
class EnvironmentEndpointImpl implements EnvironmentEndpoint {
    private final EnvironmentService environmentService
    private final BuildService buildService

    EnvironmentEndpointImpl(EnvironmentService environmentService, BuildService buildService) {
        this.environmentService = environmentService
        this.buildService = buildService
    }

    @Override
    IEnvironment save(IEnvironment environment) throws InvalidRequestException {
        IEnvironment.of(
                environmentService.save(environment)
        )
    }


    @Override
    IEnvironment get(String name) throws DataNotFoundException {
        IEnvironment.of(
                environmentService.byName(name)
                        .orElseThrow({ -> new DataNotFoundException("No Environment found with name=${name}") })
        )
    }

    @Override
    List<String> list() throws DataNotFoundException {
        environmentService.allEnvironments()
    }

    @Override
    EnvironmentBuilds verifyEnvironment(Set<IArtifact> artifacts) {
        EnvironmentBuilds toReturn = new EnvironmentBuilds(environment: 'verification-result')
        artifacts.forEach({ artifact ->
            Optional<Build> build = buildService.latestArtifact(artifact.toDomainArtifact())
            build.ifPresent(toReturn.&add)
        })
        toReturn
    }

    @Override
    void delete(String name) {
        environmentService.delete(name)
    }
}
