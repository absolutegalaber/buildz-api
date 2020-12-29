package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Artifact
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['project', 'branch', 'labels'])
class IArtifact implements Serializable, Comparable<IArtifact> {
    String project
    String branch
    Map<String, String> labels = [:]

    static IArtifact of(Artifact artifact) {
        new IArtifact(
                project: artifact.project,
                branch: artifact.branch,
                labels: artifact.labels
        )
    }

    Artifact toDomainArtifact() {
        new Artifact(
                project: project,
                branch: branch,
                labels: labels
        )
    }

    @Override
    int compareTo(IArtifact other) {
        return project <=> other.project
    }
}
