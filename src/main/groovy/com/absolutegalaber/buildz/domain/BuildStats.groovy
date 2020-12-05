package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class BuildStats implements Serializable {
    List<String> environments
    Long numberOfBuilds
    Long numberOfLabels
}
