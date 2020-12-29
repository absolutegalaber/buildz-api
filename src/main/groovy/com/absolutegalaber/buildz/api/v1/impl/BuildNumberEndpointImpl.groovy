package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.BuildNumberEndpoint
import com.absolutegalaber.buildz.domain.ProjectBranch
import com.absolutegalaber.buildz.service.BuildCountService
import com.absolutegalaber.buildz.api.model.IBuildCount
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional

@Transactional
@RestController
class BuildNumberEndpointImpl implements BuildNumberEndpoint {
    private final BuildCountService buildCountService

    BuildNumberEndpointImpl(BuildCountService buildCountService) {
        this.buildCountService = buildCountService
    }

    @Override
    IBuildCount next(ProjectBranch projectInfo) {
        IBuildCount.of(
                buildCountService.next(projectInfo.getProject(), projectInfo.getBranch())
        )
    }

    @Override
    IBuildCount current(String project, String branch) {
        IBuildCount.of(
                buildCountService.current(project, branch)
        )
    }

    @Override
    IBuildCount set(IBuildCount buildCount) {
        IBuildCount.of(
                buildCountService.set(buildCount.getProject(), buildCount.getBranch(), buildCount.getCounter())
        )
    }
}
