package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IDeploy

import com.absolutegalaber.buildz.domain.DeploySearch
import com.absolutegalaber.buildz.domain.DeploySearchResult
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Unroll

class DeployEndpointImplTest extends BaseRestSpec {

    @Unroll("#message")
    def "Search"() {
        given:
        String serverName = 'Test-Server-1'

        when:
        DeploySearch ds = new DeploySearch(serverName: serverName, page: page)
        ResponseEntity<DeploySearchResult> responseEntity = restTemplate.postForEntity(
                "http://localhost:${port}/api/v1/deploys/on",
                ds,
                DeploySearchResult
        )

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.getBody().deploys.isEmpty()

        where:
        page | message
        null | "testing with default page"
        1    | "testing with second page of deploys"
    }

    @Unroll('#message')
    def 'POST byServerAt'() {
        when:
        // First fetch all deploys...
        DeploySearch ds = new DeploySearch(serverName: 'Test-Server-1', pageSize: 100)
        ResponseEntity<DeploySearchResult> searchResponse = restTemplate.postForEntity(
                "http://localhost:${port}/api/v1/deploys/on",
                ds,
                DeploySearchResult
        )

        // And then find the most recent Deploy BEFORE the givenDate
        long idFromSearch = searchResponse.getBody().deploys.stream()
                .filter({ d -> d.deployedAt.before(givenDate) })
                // This is probably overkill since the deploys will already be ordered,
                // in descending order, so after the filter the most recent should be
                // on the top.
                .min({ d1, d2 ->
                    def diff1 = givenDate.getTime() - d1.deployedAt.getTime()
                    def diff2 = givenDate.getTime() - d2.deployedAt.getTime()

                    return Long.compare(diff1, diff2)
                })
                .get().id

        ResponseEntity<IDeploy> atResponse = restTemplate.postForEntity(
                "http://localhost:${port}/api/v1/deploy/on/Test-Server-1/at",
                givenDate,
                IDeploy
        )

        long idFromAt = atResponse.getBody().id

        then:
        idFromAt == idFromSearch

        where:
        givenDate                                                                             | message
        new Date()                                                                            | 'Check what Deploy is on Test Server 1 now (with a Date provided)'
        new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 1)                             | 'Check what Deploy was on Test Server 1 one hour ago'
        new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 5)                             | 'Check what Deploy was on Test Server 1 five hours ago'
        new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 365)                      | 'Check what Deploy was on Test Server 1 half a year ago'
        new Date(System.currentTimeMillis() - 1000 * 60 * 60 * new Random().nextInt(24))      | 'Check what Deploy was on Test Server 1 a random number of hours ago (max 1 day ago)'
    }

    @Unroll('#message')
    def "Invalid POST byServerAt"() {
        when:
        ResponseEntity<IDeploy> response = restTemplate.postForEntity(
                "http://localhost:${port}/api/v1/deploy/on/${serverName}/at",
                givenDate,
                IDeploy
        )

        then:
        response.statusCode == statusCode

        where:
        givenDate                                        | serverName       | statusCode                        | message
        new Date(System.currentTimeMillis() + 1000 * 60) | 'Test-Server-1'  | HttpStatus.BAD_REQUEST            | 'Request failed because the provided date is in the future'
        new Date()                                       | 'Empty-Server'   | HttpStatus.NOT_FOUND              | 'Request failed because the server is "empty" (i.e. no deploys are on it)'
        null                                             | 'Test-Server-1'  | HttpStatus.UNSUPPORTED_MEDIA_TYPE | 'Request failed because no date was provided (no Body/JSON)'
        new Date()                                       | null             | HttpStatus.NOT_FOUND              | 'Request failed because of missing server name'
    }

    def "Get"() {
        given:
        Long deployId = 1
        String GET_URL = "http://localhost:${port}/api/v1/deploys/${deployId}"

        when:
        ResponseEntity<IDeploy> response = restTemplate.getForEntity(GET_URL, IDeploy)

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.body.id == 1
    }

    def "Register"() {
        given:
        String REGISTER_URL = "http://localhost:${port}/api/v1/deploys/create"
        RegisterDeployEvent body = new RegisterDeployEvent(
                serverName: serverName,
                project: project,
                branch: 'main',
                buildNumber: 1,
                labels: ['type': 'hotfix']
        )
        ResponseEntity<IDeploy> response = restTemplate.postForEntity(REGISTER_URL, body, IDeploy)

        expect:
        response.statusCode == HttpStatus.OK

        and:
        response.body.id

        and:
        !response.body.labels.isEmpty()

        where:
        project    | serverName
        "backend"  | 'MyShinyNewServer'
        "frontend" | "Test-Server-1"
    }

    def "Register With Wrong data"() {
        given:
        String REGISTER_URL = "http://localhost:${port}/api/v1/deploys/create"
        RegisterDeployEvent body = new RegisterDeployEvent(
                serverName: 'MYShinyNewServer',
                project: 'noSuchProject',
                branch: 'main',
                buildNumber: 1,
                labels: ['type': 'hotfix']
        )
        when:
        ResponseEntity<IDeploy> response = restTemplate.postForEntity(REGISTER_URL, body, IDeploy)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }

    def "Multiple registrations"() {
        when:
        String REGISTER_URL = "http://localhost:${port}/api/v1/deploys/create"
        RegisterDeployEvent firstRegistration = new RegisterDeployEvent(
                serverName: 'Test-Server-1',
                project: 'backend',
                branch: 'main',
                buildNumber: 1
        )
        restTemplate.postForEntity(REGISTER_URL, firstRegistration, IDeploy)

        RegisterDeployEvent secondRegistration = new RegisterDeployEvent(
                serverName: 'Test-Server-1',
                project: 'backend',
                branch: 'next',
                buildNumber: 1
        )
        restTemplate.postForEntity(REGISTER_URL, firstRegistration, IDeploy)
        ResponseEntity<IDeploy> response = restTemplate.postForEntity(REGISTER_URL, secondRegistration, IDeploy)

        then:
        response.statusCode == HttpStatus.OK

    }

    def "AddLabels"() {
        given:
        Long deployId = 1
        String ADD_LABEL_URL = "http://localhost:${port}/api/v1/deploys/add-labels/${deployId}"
        Map<String, String> labels = [
                'user'  : 'admin',
                'reason': 'for_fun'
        ]

        when:
        ResponseEntity<IDeploy> response = restTemplate.postForEntity(ADD_LABEL_URL, labels, IDeploy)

        then:
        response.statusCode == HttpStatus.OK

        and:
        !response.body.labels.isEmpty()
    }
}
