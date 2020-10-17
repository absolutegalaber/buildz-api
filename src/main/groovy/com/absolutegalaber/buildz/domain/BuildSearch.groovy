package com.absolutegalaber.buildz.domain

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.util.StringUtils

class BuildSearch {
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
        int thePage = getPage() != null ? getPage() : 0;
        int thePageSize = getPageSize() != null ? getPageSize() : 10;
        Sort.Direction theDirection = !StringUtils.hasText(getSortDirection()) ? Sort.Direction.DESC : Sort.Direction.fromString(getSortDirection());
        String theSortAttribute = !StringUtils.hasText(getSortAttribute()) ? "buildNumber" : getSortAttribute();
        PageRequest.of(thePage, thePageSize, Sort.by(theDirection, theSortAttribute));
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
