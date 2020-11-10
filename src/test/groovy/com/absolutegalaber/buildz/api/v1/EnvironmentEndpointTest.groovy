package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class EnvironmentEndpointTest extends BaseRestSpec {
    def "Create new and delete it"() {
        given:
        String newEnvName = 'new-crazy-env'
        String CREATE_URL = "http://localhost:${port}/api/v1/environments"
        String DELETE_URL = "http://localhost:${port}/api/v1/environments/${newEnvName}"
        Environment newEnvironment = new Environment(
                name: newEnvName,
                artifacts: [
                        new Artifact(
                                project: 'backend',
                                branch: 'main'
                        )
                ]
        )

        when:
        ResponseEntity<Environment> created = restTemplate.postForEntity(CREATE_URL, newEnvironment, Environment)
        restTemplate.delete(DELETE_URL)

        then:
        created.statusCode == HttpStatus.OK

    }

    def "Get"() {
        given:
        String envName = 'main'
        String GET_URL = "http://localhost:${port}/api/v1/environments/${envName}"

        when:
        ResponseEntity<Environment> responseEntity = restTemplate.getForEntity(GET_URL, Environment)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id
    }

    def "VerifyEnvironment with 1 articfact"() {
        given:
        String VERIFY_URL = "http://localhost:${port}/api/v1/environments/verify-artifacts"
        Artifact artifact = new Artifact(
                project: 'backend',
                branch: 'main',
                labels: ['integration-test': 'ok']
        )
        when:
        ResponseEntity<EnvironmentBuilds> responseEntity = restTemplate.postForEntity(VERIFY_URL, [artifact], EnvironmentBuilds)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() == 1
    }
}
