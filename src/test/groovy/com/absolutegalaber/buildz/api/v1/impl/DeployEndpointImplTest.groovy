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
        when:
        DeploySearch ds = new DeploySearch(page: page)
        ResponseEntity<DeploySearchResult> responseEntity = restTemplate.postForEntity(
                "http://localhost:${port}/api/v1/deploys/on/Test Server 1",
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
        project      | serverName
        "backend"    | 'MyShinyNewServer'
        "frontend"   | "Test Server 1"
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
                serverName: 'Test Server 1',
                project: 'backend',
                branch: 'main',
                buildNumber: 1
        )
        restTemplate.postForEntity(REGISTER_URL, firstRegistration, IDeploy)

        RegisterDeployEvent secondRegistration = new RegisterDeployEvent(
                serverName: 'Test Server 1',
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
