package com.absolutegalaber.buildz.domain.view

import com.absolutegalaber.buildz.domain.Deploy
import com.absolutegalaber.buildz.domain.DeployLabel
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@ToString
@EqualsAndHashCode
class DeployView {
    Long id
    Date deployedAt
    DeployBuildView build
    Set<DeployLabel> labels = []

    DeployView() {}

    DeployView(Deploy deploy) {
        id = deploy.getId()
        deployedAt = deploy.getDeployedAt()
        build = new DeployView.DeployBuildView(
                project: deploy.getBuild().getProject(),
                branch: deploy.getBuild().getBranch(),
                buildNumber: deploy.getBuild().getBuildNumber()
        )
        labels = deploy.getLabels()
    }

    @ToString
    @EqualsAndHashCode
    static class DeployBuildView {
        String project
        String branch
        Long buildNumber
    }
}
