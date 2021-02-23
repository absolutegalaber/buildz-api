package com.absolutegalaber.buildz.domain

import com.absolutegalaber.buildz.api.model.IBuild
import com.absolutegalaber.buildz.api.model.IDeploy
import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.domain.Page

/**
 * A convenience class which returns the search results
 * related to {@class Deploy}s
 */
@ToString(includeSuperProperties = true)
@EqualsAndHashCode(callSuper = true)
class DeploySearchResult extends SearchResult<IDeploy> {

    /**
     * Default Constructor needed for JSON generating libraries
     */
    DeploySearchResult() {}

    private DeploySearchResult(SearchResult sr) {
        super(sr)
    }

    /**
     * Generate a DeploySearchResult based on a query Page.
     *
     * @param result A page of a query
     * @return the DeploySearchResult based on the query Page
     */
    static DeploySearchResult fromPageResult(Page<Deploy> result) {
        new DeploySearchResult(fromPageResult(result, IDeploy.&of))
    }

    @JsonIgnore
    List<IDeploy> getResults() {
        return super.results
    }

    List<IDeploy> getDeploys() {
        return super.results
    }
}
