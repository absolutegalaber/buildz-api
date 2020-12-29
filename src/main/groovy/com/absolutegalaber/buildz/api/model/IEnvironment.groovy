package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Environment
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['id', 'name', 'artifacts'])
class IEnvironment implements Serializable {
    Long id
    String name
    List<IArtifact> artifacts = []

    static IEnvironment of(Environment environment) {
        new IEnvironment(
                id: environment.id,
                name: environment.name,
                artifacts: environment.artifacts.collect {
                    Artifact artifact -> IArtifact.of(artifact)
                }
        )
    }

}
