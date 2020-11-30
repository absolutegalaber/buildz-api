package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class BuildStats implements Serializable {
    List<String> projects
    Map<String, List<String>> projectBranches = new HashMap<>()
    Set<String> environments
    Set<String> labelKeys
    Long numberOfBuilds
    Long numberOfLabels
}
