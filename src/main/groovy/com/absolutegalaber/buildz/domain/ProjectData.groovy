package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ProjectData implements Serializable {
    List<ProjectStatus> projects = new ArrayList<>()
    Map<String, List<BranchStatus>> projectBranches = new HashMap<>()
    List<String> labelKeys = new ArrayList<>()
}
