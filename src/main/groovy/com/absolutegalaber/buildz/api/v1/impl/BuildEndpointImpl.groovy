package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.BuildEndpoint
import com.absolutegalaber.buildz.domain.Build
import com.absolutegalaber.buildz.domain.BuildSearch
import com.absolutegalaber.buildz.domain.BuildSearchResult
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.service.BuildService
import com.absolutegalaber.buildz.service.ProjectService
import com.absolutegalaber.buildz.api.model.IBuild
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
    IBuild get(Long buildId) throws DataNotFoundException {
        Build build = buildService.byId(buildId).orElseThrow(
                { -> new DataNotFoundException("No Build found for buildId=" + buildId) }
        )
        IBuild.of(
                build
        )
    }

    @Override
    IBuild create(IBuild build) {
        Build toReturn = buildService.create(build.getProject(), build.getBranch(), build.getBuildNumber())
        projectService.trackProjectAndBranch(build.getProject(), build.getBranch())
        IBuild.of(
                toReturn
        )
    }

    @Override
    IBuild addLabels(Long buildId, Map<String, String> labels) throws InvalidRequestException {
        IBuild.of(
                buildService.addLabels(buildId, labels)
        )
    }

    @Override
    EnvironmentBuilds environment(String name) throws DataNotFoundException {
        buildService.ofEnvironment(name)
    }
}
