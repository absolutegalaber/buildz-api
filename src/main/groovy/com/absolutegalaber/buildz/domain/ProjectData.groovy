package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ProjectData implements Serializable {
    List<String> projects = new ArrayList<>()
    Map<String, List<String>> projectBranches = new HashMap<>()
    List<String> labelKeys = new ArrayList<>()
}
