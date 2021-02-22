package com.absolutegalaber.buildz.domain

import com.absolutegalaber.buildz.api.model.IBuild
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.domain.Page

@ToString(includeSuperProperties = true)
@EqualsAndHashCode(callSuper = true)
final class BuildSearchResult extends SearchResult<IBuild> {

    /**
     * Default Constructor needed for JSON generating libraries
     */
    BuildSearchResult() {}

    private BuildSearchResult(SearchResult sr) {
        super(sr.results, sr.page, sr.totalElements, sr.totalPages, sr.hasNext, sr.hasPrevious)
    }

    /**
     * Generate a BuildSearchResult based on a query Page.
     *
     * @param result A page of a query
     * @return the BuildSearchResult based on the query Page
     */
    static BuildSearchResult fromPageResult(Page<Build> result) {
        new BuildSearchResult(fromPageResult(result, IBuild.&of))
    }

    @JsonIgnore
    List<IBuild> getResults() {
        return super.results
    }

    List<IBuild> getBuilds() {
        return super.results
    }
}
