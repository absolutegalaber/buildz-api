package com.absolutegalaber.buildz.domain

import com.absolutegalaber.buildz.api.model.IBuild
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includes = ['environment', 'builds'])
@EqualsAndHashCode(includes = ['environment', 'builds'])
class EnvironmentBuilds implements Serializable {
    String environment
    boolean internal
    Map<String, IBuild> builds = new TreeMap<String, IBuild>()

    void add(Build build) {
        builds.put(build.getProject(), IBuild.of(build))
    }
}
