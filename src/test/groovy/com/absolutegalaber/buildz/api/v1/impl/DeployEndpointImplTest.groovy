package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IDeploy
import com.absolutegalaber.buildz.events.RegisterDeployEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DeployEndpointImplTest extends BaseRestSpec {
    def "List"() {
        given:
        String serverName = 'Test Server 1'
        String LIST_URKL = "http://localhost:${port}/api/v1/deploys/on/${serverName}"

        when:
        ResponseEntity<List> response = restTemplate.getForEntity(LIST_URKL, List)

        then:
        response.statusCode == HttpStatus.OK

        and:
        !response.body.isEmpty()
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
                serverName: 'MYShinyNewServer',
                project: 'backend',
                branch: 'main',
                buildNumber: 1,
                labels: ['type': 'hotfix']
        )
        when:
        ResponseEntity<IDeploy> response = restTemplate.postForEntity(REGISTER_URL, body, IDeploy)

        then:
        response.statusCode == HttpStatus.OK
        and:
        response.body.id
        and:
        !response.body.labels.isEmpty()
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
