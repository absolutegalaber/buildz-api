package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IBuild
import com.absolutegalaber.buildz.api.test.TestHttpEntity
import com.absolutegalaber.buildz.domain.BuildSearch
import com.absolutegalaber.buildz.domain.BuildSearchResult
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildEndpointImplTest extends BaseRestSpec {
    def "Search"() {
        given:
        String SEARCH_URL = "http://localhost:${port}/api/v1/builds/search"

        TestHttpEntity entity = new TestHttpEntity(new BuildSearch(project: 'backend'))

        when:
        ResponseEntity<BuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, entity, BuildSearchResult)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.getBody().builds.isEmpty()
    }

    def "Search with Labels"() {
        given:
        String SEARCH_URL = "http://localhost:${port}/api/v1/builds/search"
        TestHttpEntity entity = new TestHttpEntity(new BuildSearch(
                project: 'backend',
                labels: [
                        'integration-test': 'broken'
                ]
        ))

        when:
        ResponseEntity<BuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, entity, BuildSearchResult)

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
        ResponseEntity<IBuild> responseEntity = restTemplate.exchange(
                GET_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                IBuild
        )

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
        ResponseEntity<IBuild> responseEntity = restTemplate.exchange(
                GET_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                IBuild
        )

        then:
        responseEntity.statusCode == HttpStatus.NOT_FOUND
    }

    def "Create"() {
        given:
        String CREATE_URL = "http://localhost:${port}/api/v1/builds/create"
        TestHttpEntity entity = new TestHttpEntity(new IBuild(
                project: 'backend',
                branch: 'hotfix',
                buildNumber: 1
        ))

        when:
        ResponseEntity<IBuild> responseEntity = restTemplate.postForEntity(CREATE_URL, entity, IBuild)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id
    }

    def "AddLabels"() {
        given:
        Long buildId = 1
        String ADD_LABEL_URL = "http://localhost:${port}/api/v1/builds/add-labels/${buildId}"
        TestHttpEntity entity = new TestHttpEntity([
                'crazy-key': 'crazy-value'
        ])

        when:
        ResponseEntity<IBuild> responseEntity = restTemplate.postForEntity(ADD_LABEL_URL, entity, IBuild)

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
        ResponseEntity<EnvironmentBuilds> responseEntity = restTemplate.exchange(
                ENVIRONMENT_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                EnvironmentBuilds
        )

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().environment == envName

        and:
        !responseEntity.getBody().builds.isEmpty()
    }
}
