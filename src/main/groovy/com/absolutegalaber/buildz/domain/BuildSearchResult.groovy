package com.absolutegalaber.buildz.domain

import org.springframework.data.domain.Page

class BuildSearchResult {
    List<Build> builds = []
    Integer page
    Long totalElements
    Integer totalPages
    Boolean hasNext
    Boolean hasPrevious

    static BuildSearchResult fromPageResult(Page<Build> result) {
        BuildSearchResult toReturn = new BuildSearchResult()
        toReturn.getBuilds().addAll(result.getContent())
        toReturn.setPage(result.getNumber())
        toReturn.setTotalPages(result.getTotalPages())
        toReturn.setTotalElements(result.getTotalElements())
        toReturn.setHasNext(result.hasNext())
        toReturn.setHasPrevious(result.hasPrevious())
        toReturn
    }
}
