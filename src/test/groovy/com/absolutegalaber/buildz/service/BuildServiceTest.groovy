package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.*
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildServiceTest extends com.absolutegalaber.buildz.BaseBuildzSpec {
    @Subject
    @Autowired
    BuildService service

    @Unroll("#message")
    def "ById"() {
        when:
        Optional<Build> build = service.byId(id)

        then:
        build.isPresent() == expected

        where:
        id  | expected | message
        1L  | true     | 'ById finds existing Build'
        -1L | false    | 'ById is empty for missing Build'

    }

    def "Create"() {
        expect:
        service.create('backend', 'next', 1).id
    }

    def "AddLabels"() {
        given:
        BuildLabel label = new BuildLabel()
        label.setKey('newKey')
        label.setValue('newValue')

        when:
        Build theBuild = service.addLabels(1L, [label])

        then:
        theBuild.labels.size() == 4

    }

    def "AddLabels with wrong build id"() {
        given:
        BuildLabel label = new BuildLabel()
        label.setKey('newKey')
        label.setValue('newValue')

        when:
        service.addLabels(-1L, [label])

        then:
        thrown(InvalidRequestException)
    }

    @Unroll("#message")
    def "Search"() {
        given:
        BuildSearch buildSearch = new BuildSearch(
                project: theProject,
                branch: theBranch,
                minBuildNumber: theMinBuildNumber,
                maxBuildNumber: theMaxBuildNumber,
                pageSize: 100,
                page: 0
        )
        if (theLabelKey && theLabelValue) {
            buildSearch.labels[theLabelKey] = theLabelValue
        }
        when:
        BuildSearchResult result = service.search(buildSearch)

        then:
        result.builds.size() == expected

        and:
        if (theProject) {
            result.builds.project.every {
                it == theProject
            }
        }

        and:
        if (theBranch) {
            result.builds.branch.every {
                it == theBranch
            }
        }


        where:
        theProject | theBranch | theLabelKey        | theLabelValue  | expected | theMinBuildNumber | theMaxBuildNumber | message
        null       | null      | null               | null           | 90       | null              | null              | "Search(): finds all builds for empty search"
        null       | null      | null               | null           | 9        | 1                 | 3                 | "Search(): finds all builds with build number = 2"
        'backend'  | null      | null               | null           | 30       | null              | null              | "Search(): finds all builds of project 'backend'"
        'backend'  | 'main'    | null               | null           | 10       | null              | null              | "Search(): finds all builds of project 'backend' of branch 'master"
        'backend'  | 'main'    | 'integration-test' | 'ok'           | 5        | null              | null              | "Search(): finds all builds of projects with label technical_branch=feature/some-other-feature"
        null       | null      | 'technical_branch' | 'noSuchBranch' | 0        | null              | null              | "Search(): is empty for empty lbel sub-search"
    }

    @Unroll("#message")
    def "Latest()"() {
        Artifact artifact = new Artifact(
                project: theProject,
                branch: theBranch
        )
        if (theLabelKey && theLabelValue) {
            artifact.getLabels().put(theLabelKey, theLabelValue)
        }
        when:
        Optional<Build> latest = service.latestArtifact(artifact)

        then:
        if (expectedBuildNumber) {
            latest.isPresent()
            latest.get().getBuildNumber() == expectedBuildNumber
        } else {
            !latest.isPresent()
        }

        where:
        theProject | theBranch        | theLabelKey        | theLabelValue | expectedBuildNumber | message
        'backend'  | 'main'           | null               | null          | 10L                 | "LatestArtifact(): finds latest build of branch master"
        'backend'  | 'main'           | 'integration-test' | 'ok'          | 9L                  | "LatestArtifact(): finds latest build for a specific label"
        'backend'  | null             | 'doesnot'          | 'exist'       | null                | "LatestArtifact(): is empty for missing labels"
        'backend'  | 'deleted-branch' | null               | null          | null                | "LatestArtifact(): is empty for missing project/branch combo"
    }

    @Unroll("#message")
    def "OfEnvironment"() {
        when:
        EnvironmentBuilds builds = service.ofEnvironment(envName)

        then:
        builds.builds.size() == expectedSize

        where:
        envName                | expectedSize | message
        'main'                 | 2            | "OfEnvironment(): returns 2 builds for well - defined Environment (#envName)"
        'feature-test-stage-1' | 0            | "OfEnvironment(): returns 0 builds for un-defined Environment (#envName)"
    }

    def "OfEnvironment with wrong env name"() {
        when:
        service.ofEnvironment('no-such-env')

        then:
        thrown(InvalidRequestException)
    }
}
