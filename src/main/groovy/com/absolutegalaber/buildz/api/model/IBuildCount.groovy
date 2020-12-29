package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.BuildCount
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['project', 'branch', 'counter'])
class IBuildCount implements Serializable {
    String project
    String branch
    Long counter

    static IBuildCount of(BuildCount buildCount) {
        new IBuildCount(
                project: buildCount.project,
                branch: buildCount.branch,
                counter: buildCount.counter
        )
    }
}
