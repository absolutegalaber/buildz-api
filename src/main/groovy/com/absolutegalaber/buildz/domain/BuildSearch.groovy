package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.domain.Sort

@ToString(includes = ['project', 'branch', 'minBuildNumber', 'maxBuildNumber', 'labels'])
@EqualsAndHashCode(includes = ['project', 'branch', 'minBuildNumber', 'maxBuildNumber', 'labels'])
class BuildSearch extends BaseSearch {
    private static final DEFAULT_SORT_ATTRIBUTE = 'buildNumber'
    String project
    String branch
    Long minBuildNumber
    Long maxBuildNumber
    Map<String, String> labels = [:]

    static BuildSearch fromArtifact(Artifact artifact) {
        new BuildSearch(
                project: artifact.project,
                branch: artifact.branch,
                labels: artifact.labels,
                pageSize: 1,
                page: 0,
                sortAttribute: DEFAULT_SORT_ATTRIBUTE,
                sortDirection: Sort.Direction.DESC.name()
        )
    }

    protected String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE
    }
}
