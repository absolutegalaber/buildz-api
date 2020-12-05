package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.BuildEndpoint
import com.absolutegalaber.buildz.domain.*
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.ProjectService
import com.absolutegalaber.buildz.service.StatsService
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional

@RestController
@Transactional
class BuildEndpointImpl implements BuildEndpoint {
    private final BuildService buildService
    private final ProjectService projectService

    BuildEndpointImpl(BuildService buildService, ProjectService projectService) {
        this.buildService = buildService
        this.projectService = projectService
    }

    @Override
    BuildSearchResult search(BuildSearch search) {
        buildService.search(search)
    }

    @Override
    Build get(Long buildId) throws DataNotFoundException {
        Optional<Build> build = buildService.byId(buildId)
        build.orElseThrow(
                { -> new DataNotFoundException("No Build found for buildId=" + buildId) }
        )
    }

    @Override
    Build create(Build build) {
        Build toReturn = buildService.create(build.getProject(), build.getBranch(), build.getBuildNumber())
        projectService.trackProject(build.getProject())
        projectService.trackBranchOf(build.getProject(), build.getBranch())
        toReturn
    }

    @Override
    Build addLabels(Long buildId, List<BuildLabel> buildLabels) throws InvalidRequestException {
        buildService.addLabels(buildId, buildLabels)
    }

    @Override
    EnvironmentBuilds environment(String name) throws InvalidRequestException {
        buildService.ofEnvironment(name)
    }
}
