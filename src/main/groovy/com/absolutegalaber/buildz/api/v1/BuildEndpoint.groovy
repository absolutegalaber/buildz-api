package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.domain.*
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.StatsService
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@RestController
@Transactional
class BuildEndpoint {
    private final BuildService buildService
    private final StatsService statsService

    BuildEndpoint(BuildService buildService, StatsService statsService) {
        this.buildService = buildService
        this.statsService = statsService
    }

    @GetMapping('/api/v1/builds/stats')
    BuildStats stats() {
        statsService.stats()
    }

    @PostMapping('/api/v1/builds/search')
    BuildSearchResult search(@RequestBody BuildSearch search) {
        buildService.search(search)
    }

    @GetMapping('/api/v1/builds/{buildId}')
    Build get(@PathVariable(name = 'buildId') Long buildId) throws DataNotFoundException {
        Optional<Build> build = buildService.byId(buildId)
        build.orElseThrow(
                { -> new DataNotFoundException("No Build found for buildId=" + buildId) }
        )
    }

    @PostMapping('/api/v1/builds/create')
    Build create(@RequestBody Build build) {
        return buildService.create(build.getProject(), build.getBranch(), build.getBuildNumber())
    }

    @PostMapping('/api/v1/builds/add-labels/{buildId}')
    Build addLabels(@PathVariable(name = "buildId") Long buildId, @RequestBody List<BuildLabel> buildLabels) throws InvalidRequestException {
        buildService.addLabels(buildId, buildLabels)
    }

    @GetMapping('/api/v1/builds/of-environment/{name}')
    EnvironmentBuilds environment(@PathVariable(name = 'name') String name) throws InvalidRequestException {
        buildService.ofEnvironment(name)
    }
}
