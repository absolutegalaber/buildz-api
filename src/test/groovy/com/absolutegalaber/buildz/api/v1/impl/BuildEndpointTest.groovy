package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildEndpointTest extends BaseRestSpec {
    def "Search"() {
        given:
        String SEARCH_URL = "http://localhost:${port}/api/v1/builds/search"
        BuildSearch search = new BuildSearch(
                project: 'backend'
        )

        when:
        ResponseEntity<BuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, BuildSearchResult)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.getBody().builds.isEmpty()
    }

    def "Search with Labels"() {
        given:
        String SEARCH_URL = "http://localhost:${port}/api/v1/builds/search"
        BuildSearch search = new BuildSearch(
                project: 'backend',
                labels: [
                        'integration-test': 'broken'
                ]
        )

        when:
        ResponseEntity<BuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, BuildSearchResult)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.getBody().builds.isEmpty()
    }

    def "Get"() {
        given:
        Long buildId = 1
        String GET_URL = "http://localhost:${port}/api/v1/builds/${buildId}"

        when:
        ResponseEntity<Build> responseEntity = restTemplate.getForEntity(GET_URL, Build)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id == buildId
    }

    def "Get non existing Build"() {
        given:
        Long buildId = -1
        String GET_URL = "http://localhost:${port}/api/v1/builds/${buildId}"

        when:
        ResponseEntity<Build> responseEntity = restTemplate.getForEntity(GET_URL, Build)

        then:
        responseEntity.statusCode == HttpStatus.NOT_FOUND
    }

    def "Create"() {
        given:
        String CREATE_URL = "http://localhost:${port}/api/v1/builds/create"
        Build build = new Build(
                project: 'backend',
                branch: 'hotfix',
                buildNumber: 1
        )

        when:
        ResponseEntity<Build> responseEntity = restTemplate.postForEntity(CREATE_URL, build, Build)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id
    }

    def "AddLabels"() {
        given:
        Long buildId = 1
        String ADD_LABEL_URL = "http://localhost:${port}/api/v1/builds/add-labels/${buildId}"
        List<BuildLabel> newLabels = [
                new BuildLabel(key: 'crazy-key', value: 'crazy-value')
        ]

        when:
        ResponseEntity<Build> responseEntity = restTemplate.postForEntity(ADD_LABEL_URL, newLabels, Build)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().labels.collect { it.key }.contains('crazy-key')
    }

    def "Environment"() {
        given:
        String envName = 'main'
        String ENVIRONMENT_URL = "http://localhost:${port}/api/v1/builds/of-environment/${envName}"

        when:
        ResponseEntity<EnvironmentBuilds> responseEntity = restTemplate.getForEntity(ENVIRONMENT_URL, EnvironmentBuilds)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().environment == envName

        and:
        !responseEntity.getBody().builds.isEmpty()
    }
}
