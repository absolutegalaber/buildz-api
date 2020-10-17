package com.absolutegalaber.buildz.domain

class EnvironmentBuilds {
    Map<String, Build> builds = [:]

    void add(Build build) {
        builds.put(build.getProject(), build);
    }
}
