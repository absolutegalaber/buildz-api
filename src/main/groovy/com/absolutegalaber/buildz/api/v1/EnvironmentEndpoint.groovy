package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.EnvironmentService
import org.springframework.stereotype.Component

import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Component
@Path('v1/environments')
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
class EnvironmentEndpoint {
    private final EnvironmentService environmentService
    private final BuildService buildService

    EnvironmentEndpoint(EnvironmentService environmentService, BuildService buildService) {
        this.environmentService = environmentService
        this.buildService = buildService
    }

    @POST
    Environment save(Environment environment) throws InvalidRequestException {
        return environmentService.save(environment)
    }

    @GET
    @Path('/{name}')
    Environment get(@PathParam('name') String name) throws InvalidRequestException {
        return environmentService.byName(name).orElseThrow({ -> new InvalidRequestException("No Environment found with name=${name}") })
    }

    @POST
    @Path('/verify-artifacts')
    EnvironmentBuilds verifyEnvironment(Set<Artifact> artifacts) {
        EnvironmentBuilds toReturn = new EnvironmentBuilds(environment: 'verification-result')
        artifacts.forEach({ artifact ->
            Optional<Build> build = buildService.latestArtifact(artifact)
            build.ifPresent(toReturn.&add)
        })
        return toReturn
    }

    @DELETE
    @Path("/{name}")
    void delete(@PathParam('name') String name) {
        environmentService.delete(name)
    }
}
