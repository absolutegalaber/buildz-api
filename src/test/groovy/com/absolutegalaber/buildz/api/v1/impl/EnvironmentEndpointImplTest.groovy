package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IArtifact
import com.absolutegalaber.buildz.api.model.IEnvironment
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import com.absolutegalaber.buildz.domain.ExceptionInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class EnvironmentEndpointImplTest extends BaseRestSpec {
    def "Create new and delete it"() {
        given:
        String newEnvName = 'new-crazy-env'
        String CREATE_URL = "http://localhost:${port}/api/v1/environments"
        String DELETE_URL = "http://localhost:${port}/api/v1/environments/${newEnvName}"
        IEnvironment newEnvironment = new IEnvironment(
                name: newEnvName,
                artifacts: [
                        new IArtifact(
                                project: 'backend',
                                branch: 'main'
                        )
                ]
        )

        when:
        ResponseEntity<IEnvironment> created = restTemplate.postForEntity(CREATE_URL, newEnvironment, IEnvironment)
        restTemplate.delete(DELETE_URL)

        then:
        created.statusCode == HttpStatus.OK

    }

    def "Get"() {
        given:
        String envName = 'main'
        String GET_URL = "http://localhost:${port}/api/v1/environments/${envName}"

        when:
        ResponseEntity<IEnvironment> responseEntity = restTemplate.getForEntity(GET_URL, IEnvironment)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id
    }

    def "Get DataNotFound"() {
        given:
        String envName = 'wrongName'
        String GET_URL = "http://localhost:${port}/api/v1/environments/${envName}"

        when:
        ResponseEntity<ExceptionInfo> responseEntity = restTemplate.getForEntity(GET_URL, ExceptionInfo)

        then:
        responseEntity.statusCode == HttpStatus.NOT_FOUND

        and:
        responseEntity.getBody().key == 'not-found'
    }

    def "VerifyEnvironment with 1 articfact"() {
        given:
        String VERIFY_URL = "http://localhost:${port}/api/v1/environments/verify-artifacts"
        IArtifact artifact = new IArtifact(
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

    def "List all userdefined Envs"() {
        given:
        String LIST_URL = "http://localhost:${port}/api/v1/environments/"

        when:
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(LIST_URL, List)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.body.isEmpty()
    }

    def "List all Envs"() {
        given:
        String LIST_URL = "http://localhost:${port}/api/v1/environments/all"

        when:
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(LIST_URL, List)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.body.isEmpty()

        and:
        (responseEntity.body.first() as IEnvironment).id
    }
}
