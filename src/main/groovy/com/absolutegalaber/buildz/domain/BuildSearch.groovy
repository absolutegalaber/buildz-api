package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@ToString(includes = ['project', 'branch', 'minBuildNumber', 'maxBuildNumber', 'labels'])
@EqualsAndHashCode(includes = ['project', 'branch', 'minBuildNumber', 'maxBuildNumber', 'labels'])
class BuildSearch implements Serializable {
    String project
    String branch
    Long minBuildNumber
    Long maxBuildNumber
    Map<String, String> labels = [:]
    Integer pageSize
    Integer page
    String sortAttribute
    String sortDirection

    PageRequest page() {
        int thePage = page ?: 0
        int thePageSize = pageSize ?: 10
        Sort.Direction theDirection = sortDirection ? Sort.Direction.fromString(sortDirection) : Sort.Direction.DESC
        String theSortAttribute = sortAttribute ?: "buildNumber"
        PageRequest.of(thePage, thePageSize, Sort.by(theDirection, theSortAttribute))
    }

    static BuildSearch fromArtifact(Artifact artifact) {
        new BuildSearch(
                project: artifact.project,
                branch: artifact.branch,
                labels: artifact.labels,
                pageSize: 1,
                page: 0,
                sortAttribute: 'buildNumber',
                sortDirection: Sort.Direction.DESC.name()
        )
    }
}
