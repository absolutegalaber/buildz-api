package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includes = ['environment', 'builds'])
@EqualsAndHashCode(includes = ['environment', 'builds'])
class EnvironmentBuilds implements Serializable {
    String environment
    Map<String, Build> builds = new TreeMap<String, Build>()

    void add(Build build) {
        builds.put(build.getProject(), build)
    }
}
