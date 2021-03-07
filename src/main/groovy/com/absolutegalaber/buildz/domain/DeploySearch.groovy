package com.absolutegalaber.buildz.domain

class DeploySearch extends BaseSearch {
    private static final DEFAULT_SORT_ATTRIBUTE = 'id'

    String serverName

    protected String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE
    }
}
