package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Build
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['project', 'branch', 'buildNumber'])
class IBuild implements Serializable {
    Long id
    String project
    String branch
    Long buildNumber
    Map<String, String> labels = [:]

    static IBuild of(Build build) {
        IBuild toReturn = new IBuild(
                id: build.id,
                project: build.project,
                branch: build.branch,
                buildNumber: build.buildNumber,
        )
        build.labels.each {
            toReturn.labels.put(it.key, it.value)
        }
        toReturn
    }
}
