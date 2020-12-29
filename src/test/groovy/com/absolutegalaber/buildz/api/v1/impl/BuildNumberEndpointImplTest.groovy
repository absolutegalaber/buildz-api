package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IBuildCount
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildNumberEndpointImplTest extends BaseRestSpec {

    def "Current() for existing project"() {
        given:
        String project = 'backend'
        String branch = 'main'
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/build-numbers/current/${project}/${branch}"

        when:
        ResponseEntity<IBuildCount> current = restTemplate.getForEntity(GET_CURRENT_URL, IBuildCount)
        IBuildCount buildCount = current.getBody()

        then:
        current.statusCode == HttpStatus.OK

        and:
        buildCount.counter == 10
    }

    def "Current() on new project"() {
        given:
        String project = 'backend'
        String branch = 'master'
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/build-numbers/current/${project}/${branch}"

        when:
        ResponseEntity<IBuildCount> first = restTemplate.getForEntity(GET_CURRENT_URL, IBuildCount)
        ResponseEntity<IBuildCount> second = restTemplate.getForEntity(GET_CURRENT_URL, IBuildCount)

        then:
        first.statusCode == HttpStatus.OK

        and:
        second.statusCode == HttpStatus.OK
        first.getBody().counter == second.getBody().counter

    }

    def "Next() and Set()"() {
        given:
        String project = 'backend'
        String branch = 'feature/feature-backend'
        IBuildCount nextBuildCount = new IBuildCount(
                project: project,
                branch: branch
        )
        IBuildCount setBuildCount = new IBuildCount(
                project: project,
                branch: branch,
                counter: 20
        )

        String NEXT_URL = "http://localhost:${port}/api/v1/build-numbers/next"
        String SET_URL = "http://localhost:${port}/api/v1/build-numbers/set"

        when:
        //Call next
        ResponseEntity<IBuildCount> nextResponse = restTemplate.postForEntity(NEXT_URL, nextBuildCount, IBuildCount)
        ResponseEntity<IBuildCount> setResponse = restTemplate.postForEntity(SET_URL, setBuildCount, IBuildCount)

        then:
        nextResponse.statusCode == HttpStatus.OK
        setResponse.statusCode == HttpStatus.OK

        and:
        nextResponse.getBody().counter == 11
        setResponse.getBody().counter == 20
    }
}