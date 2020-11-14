package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class BuildStats implements Serializable {
    Set<String> projects
    Set<String> branches
    Set<String> environments
    Set<String> labelKeys
    Long numberOfBuilds
    Long numberOfLabels
}
