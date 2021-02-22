package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.domain.Page

/**
 * The results of a query
 * @param <R> the class of the results returned from the search
 */
@ToString(includes = ['results', 'page', 'totalElements', 'totalPages', 'hasNext', 'hasPrevious'])
@EqualsAndHashCode(includes = ['results', 'page', 'totalElements', 'totalPages', 'hasNext', 'hasPrevious'])
class SearchResult<R> implements Serializable {
    List<R> results = []
    Integer page
    Long totalElements
    Integer totalPages
    Boolean hasNext
    Boolean hasPrevious

    /**
     * Default constructor
     * needed for JSON
     * libraries
     */
    SearchResult() {}

    protected SearchResult(
            List<R> results,
            Integer page,
            Integer totalPages,
            Long totalElements,
            Boolean hasNext,
            Boolean hasPrevious
    ) {
        this.results = results
        this.page = page
        this.totalPages = totalPages
        this.totalElements = totalElements
        this.hasNext = hasNext
        this.hasPrevious = hasPrevious
    }

    /**
     * Returns a SearchResult based on a query Page and a closure that transforms the
     * Page's results into the relevant SearchResult result Class.
     *
     * @param result a query Page containing all info needed to build a SearchResult
     * @param transform a closure which can transform the Page's results class to the
     * SearchResult's results class
     * @return a SearchResult based on the provided Page
     */
    static <P, R> SearchResult fromPageResult(Page<P> result, Closure<R> transform) {
        new SearchResult(
            result.getContent().collect(transform),
            result.getNumber(),
            result.getTotalPages(),
            result.getTotalElements(),
            result.hasNext(),
            result.hasPrevious()
        )
    }
}
