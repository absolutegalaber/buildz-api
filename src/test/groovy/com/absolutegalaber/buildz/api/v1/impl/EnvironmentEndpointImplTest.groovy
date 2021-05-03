package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IArtifact
import com.absolutegalaber.buildz.api.model.IDeploy
import com.absolutegalaber.buildz.api.model.IEnvironment
import com.absolutegalaber.buildz.api.test.TestHttpEntity
import com.absolutegalaber.buildz.domain.EnvironmentBuilds
import com.absolutegalaber.buildz.domain.ExceptionInfo
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class EnvironmentEndpointImplTest extends BaseRestSpec {
    def "Create new and delete it"() {
        given:
        String newEnvName = 'new-crazy-env'
        String CREATE_URL = "http://localhost:${port}/api/v1/environments"
        String DELETE_URL = "http://localhost:${port}/api/v1/environments/${newEnvName}"
        TestHttpEntity entity = new TestHttpEntity(new IEnvironment(
                name: newEnvName,
                artifacts: [
                        new IArtifact(
                                project: 'backend',
                                branch: 'main'
                        )
                ]
        ))

        when:
        ResponseEntity<IEnvironment> created = restTemplate.postForEntity(CREATE_URL, entity, IEnvironment)
        restTemplate.delete(DELETE_URL)

        then:
        created.statusCode == HttpStatus.OK

    }

    def "Get"() {
        given:
        String envName = 'main'
        String GET_URL = "http://localhost:${port}/api/v1/environments/${envName}"

        when:
        ResponseEntity<IEnvironment> responseEntity = restTemplate.exchange(
                GET_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                IEnvironment
        )

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
        ResponseEntity<ExceptionInfo> responseEntity = restTemplate.exchange(
                GET_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                ExceptionInfo
        )

        then:
        responseEntity.statusCode == HttpStatus.NOT_FOUND

        and:
        responseEntity.getBody().key == 'not-found'
    }

    def "VerifyEnvironment with 1 articfact"() {
        given:
        String VERIFY_URL = "http://localhost:${port}/api/v1/environments/verify-artifacts"
        TestHttpEntity entity = new TestHttpEntity([new IArtifact(
                project: 'backend',
                branch: 'main',
                labels: ['integration-test': 'ok']
        )])
        when:
        ResponseEntity<EnvironmentBuilds> responseEntity = restTemplate.postForEntity(VERIFY_URL, entity, EnvironmentBuilds)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() == 1
    }

    def "List all userdefined Envs"() {
        given:
        String LIST_URL = "http://localhost:${port}/api/v1/environments/"

        when:
        ResponseEntity<List> responseEntity = restTemplate.exchange(
                LIST_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                List
        )

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.body.isEmpty()
    }

    def "List all Envs"() {
        given:
        String LIST_URL = "http://localhost:${port}/api/v1/environments/all"

        when:
        ResponseEntity<List> responseEntity = restTemplate.exchange(
                LIST_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                List
        )

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.body.isEmpty()

        and:
        (responseEntity.body.first() as IEnvironment).id
    }
}
