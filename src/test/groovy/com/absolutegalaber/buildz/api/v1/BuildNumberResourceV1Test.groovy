package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.BuildCount
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildNumberResourceV1Test extends BaseRestSpec {

    def "Current() for existing project"() {
        given:
        String project = 'buildz-backend'
        String branch = 'master'
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/build-numbers/current/${project}/${branch}"

        when:
        ResponseEntity<BuildCount> current = restTemplate.getForEntity(GET_CURRENT_URL, BuildCount)
        BuildCount buildCount = current.getBody()

        then:
        current.statusCode == HttpStatus.OK

        and:
        buildCount.counter == 1
    }

    def "Current() on new project"() {
        given:
        String project = 'buildz-backend'
        String branch = 'master'
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/build-numbers/current/${project}/${branch}"

        when:
        ResponseEntity<BuildCount> first = restTemplate.getForEntity(GET_CURRENT_URL, BuildCount)
        ResponseEntity<BuildCount> second = restTemplate.getForEntity(GET_CURRENT_URL, BuildCount)

        then:
        first.statusCode == HttpStatus.OK

        and:
        second.statusCode == HttpStatus.OK
        first.getBody().counter == second.getBody().counter

    }

    def "Next() and Set()"() {
        given:
        String project = 'buildz-backend'
        String branch = 'master'
        BuildCount nextBuildCount = new BuildCount(
                project: project,
                branch: branch
        )
        BuildCount setBuildCount = new BuildCount(
                project: project,
                branch: branch,
                counter: 10
        )

        String NEXTZ_URL = "http://localhost:${port}/api/v1/build-numbers/next"
        String SET_URL = "http://localhost:${port}/api/v1/build-numbers/set"

        when:
        //Call next
        ResponseEntity<BuildCount> nextResponse = restTemplate.postForEntity(NEXTZ_URL, nextBuildCount, BuildCount)
        ResponseEntity<BuildCount> setResponse = restTemplate.postForEntity(SET_URL, setBuildCount, BuildCount)

        then:
        nextResponse.statusCode == HttpStatus.OK
        setResponse.statusCode == HttpStatus.OK

        and:
        nextResponse.getBody().counter == 2
        setResponse.getBody().counter == 10
    }
}