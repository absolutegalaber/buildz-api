package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.EnvironmentEndpoint
import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.Environment
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
    Environment save(Environment environment) throws InvalidRequestException {
        return environmentService.save(environment)
    }


    @Override
    Environment get(String name) throws DataNotFoundException {
        return environmentService.byName(name).orElseThrow({ -> new DataNotFoundException("No Environment found with name=${name}") })
    }

    @Override
    EnvironmentBuilds verifyEnvironment(Set<Artifact> artifacts) {
        EnvironmentBuilds toReturn = new EnvironmentBuilds(environment: 'verification-result')
        artifacts.forEach({ artifact ->
            Optional<Build> build = buildService.latestArtifact(artifact)
            build.ifPresent(toReturn.&add)
        })
        return toReturn
    }

    @Override
    void delete(String name) {
        environmentService.delete(name)
    }
}