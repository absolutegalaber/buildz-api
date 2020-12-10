package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.BuildNumberEndpoint
import com.absolutegalaber.buildz.domain.BuildCount
import com.absolutegalaber.buildz.domain.ProjectBranch
import com.absolutegalaber.buildz.service.BuildCountService
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
    BuildCount next(ProjectBranch projectInfo) {
        return buildCountService.next(projectInfo.getProject(), projectInfo.getBranch())
    }

    @Override
    BuildCount current(String project, String branch) {
        return buildCountService.current(project, branch)
    }

    @Override
    BuildCount set(BuildCount buildCount) {
        buildCountService.set(buildCount.getProject(), buildCount.getBranch(), buildCount.getCounter())
    }
}
