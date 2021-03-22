package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includes = ['serverName'])
@EqualsAndHashCode(includes = ['serverName'], callSuper = true)
class DeploySearch extends BaseSearch {
    private static final DEFAULT_SORT_ATTRIBUTE = 'id'

    String serverName

    protected String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE
    }
}
