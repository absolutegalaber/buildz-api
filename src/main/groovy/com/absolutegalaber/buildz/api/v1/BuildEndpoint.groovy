package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.domain.*
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.StatsService
import org.springframework.stereotype.Component

import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Component
@Path('v1/builds')
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
class BuildEndpoint {
    private final BuildService buildService
    private final StatsService statsService

    BuildEndpoint(BuildService buildService, StatsService statsService) {
        this.buildService = buildService
        this.statsService = statsService
    }

    @GET
    @Path('/stats')
    BuildStats stats() {
        statsService.stats()
    }

    @POST
    @Path('/search')
    BuildSearchResult search(BuildSearch search) {
        buildService.search(search)
    }

    @GET
    @Path('/{buildId}')
    Build get(@PathParam('buildId') Long buildId) throws DataNotFoundException {
        Optional<Build> build = buildService.byId(buildId)
        build.orElseThrow(
                { -> new DataNotFoundException("No Build found for buildId=" + buildId) }
        )
    }

    @POST
    @Path('/create')
    Build create(Build build) {
        return buildService.create(build.getProject(), build.getBranch(), build.getBuildNumber())
    }

    @POST
    @Path('/add-labels/{buildId}')
    Build addLabels(@PathParam("buildId") Long buildId, List<BuildLabel> buildLabels) throws InvalidRequestException {
        buildService.addLabels(buildId, buildLabels)
    }

    @GET
    @Path('/of-environment/{name}')
    EnvironmentBuilds environment(@PathParam('name') String name) throws InvalidRequestException {
        buildService.ofEnvironment(name)
    }
}
