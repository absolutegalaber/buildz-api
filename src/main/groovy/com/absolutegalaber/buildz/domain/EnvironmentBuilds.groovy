package com.absolutegalaber.buildz.domain

class EnvironmentBuilds {
    String environment
    Map<String, Build> builds = [:]

    void add(Build build) {
        builds.put(build.getProject(), build)
    }
}
