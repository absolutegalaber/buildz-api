package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Artifact
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['project', 'branch', 'buildNumber', 'labels'])
class IArtifact implements Serializable, Comparable<IArtifact> {
    String project
    String branch
    Long buildNumber
    Map<String, String> labels = [:]

    static IArtifact of(Artifact artifact) {
        new IArtifact(
                project: artifact.project,
                branch: artifact.branch,
                buildNumber: artifact.buildNumber,
                labels: artifact.labels
        )
    }

    Artifact toDomainArtifact() {
        new Artifact(
                project: project,
                branch: branch,
                buildNumber: buildNumber,
                labels: labels
        )
    }

    @Override
    int compareTo(IArtifact other) {
        return project <=> other.project
    }
}
