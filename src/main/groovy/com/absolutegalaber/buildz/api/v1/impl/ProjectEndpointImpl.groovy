package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.ProjectEndpoint
import com.absolutegalaber.buildz.domain.ProjectData
import com.absolutegalaber.buildz.domain.ProjectInfo
import com.absolutegalaber.buildz.domain.ProjectName
import com.absolutegalaber.buildz.service.ProjectService
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional

@RestController
@Transactional
class ProjectEndpointImpl implements ProjectEndpoint {
    private final ProjectService projectService

    ProjectEndpointImpl(ProjectService projectService) {
        this.projectService = projectService
    }

    @Override
    ProjectData getProjectData(Boolean includeInactive) {
        projectService.dataForAllProjects(includeInactive)
    }

    @Override
    void toggleBranchActivation(ProjectInfo projectInfo) {
        projectService.toggleBranchActive(projectInfo.project, projectInfo.branch)
    }

    @Override
    void toggleProjectActivation(ProjectName projectName) {
        projectService.toggleProjectActive(projectName.project)
    }
}
