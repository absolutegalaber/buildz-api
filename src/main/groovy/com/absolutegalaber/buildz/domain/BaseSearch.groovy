package com.absolutegalaber.buildz.domain

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

abstract class BaseSearch implements Serializable {
    Integer pageSize
    Integer page
    String sortAttribute
    String sortDirection

    BaseSearch() {}

    PageRequest page() {
        int thePage = page ?: 0
        int thePageSize = pageSize ?: 10
        String theSortAttribute = sortAttribute ?: defaultSortAttribute()
        Sort.Direction theDirection = sortDirection ? Sort.Direction.fromString(sortDirection) : Sort.Direction.DESC
        PageRequest.of(thePage, thePageSize, Sort.by(theDirection, theSortAttribute))
    }

    abstract protected String defaultSortAttribute();
}
