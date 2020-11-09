package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.EnvironmentService
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@Transactional
class EnvironmentEndpoint {
    private final EnvironmentService environmentService
    private final BuildService buildService

    EnvironmentEndpoint(EnvironmentService environmentService, BuildService buildService) {
        this.environmentService = environmentService
        this.buildService = buildService
    }

    @PostMapping(value = '/api/v1/environments')
    Environment save(@RequestBody Environment environment) throws InvalidRequestException {
        return environmentService.save(environment)
    }

    @GetMapping('/api/v1/environments/{name}')
    Environment get(@PathVariable('name') String name) throws InvalidRequestException {
        return environmentService.byName(name).orElseThrow({ -> new InvalidRequestException("No Environment found with name=${name}") })
    }

    @PostMapping('/api/v1/environments/verify-artifacts')
    EnvironmentBuilds verifyEnvironment(@RequestBody Set<Artifact> artifacts) {
        EnvironmentBuilds toReturn = new EnvironmentBuilds(environment: 'verification-result')
        artifacts.forEach({ artifact ->
            Optional<Build> build = buildService.latestArtifact(artifact)
            build.ifPresent(toReturn.&add)
        })
        return toReturn
    }

    @DeleteMapping("/api/v1/environments/{name}")
    void delete(@PathVariable('name') String name) {
        environmentService.delete(name)
    }
}
