package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.Artifact
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.InvalidRequestException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by Josip.Mihelko @ Gmail
 */
class EnvironmentServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    EnvironmentService service

    @Unroll("#message")
    def "ByName"() {
        when:
        Optional<Environment> env = service.byName(name)

        then:
        env.isPresent() == expected

        where:
        name                  | expected | message
        'master-test-stage-1' | true     | 'ByName(): finds existing Environment by name'
        'missing'             | false    | 'ByName(): is empty for non-existing environment name'


    }

    def "Create"() {
        expect:
        service.create("new-environment").id
    }

    def "Create with duplicate name"() {
        when:
        service.create("master-test-stage-1")

        then:
        thrown(InvalidRequestException)
    }

    def "Save inserting new instance"() {
        given:
        Environment environment = new Environment()
        environment.setName("new-environment")
        Artifact artifact = new Artifact(
                project: 'some-project', branch: 'some-branch'
        )
        artifact.getLabels().put('some', 'label')
        artifact.setEnvironment(environment)
        environment.getArtifacts().add(artifact)

        when:
        Environment inserted = service.save(environment)

        then:
        inserted.id

        and:
        inserted.getArtifacts().size() == 1

        and:
        inserted.getArtifacts().first().labels.size() == 1
    }


    def "Save updating existing instance"() {
        given:
        Environment environment = service.byName('master-test-stage-1').get()
        Artifact artifact = new Artifact(
                project: 'some-project', branch: 'some-branch'
        )
        artifact.getLabels().put('some', 'label')
        artifact.setEnvironment(environment)
        environment.getArtifacts().add(artifact)


        when:
        Environment inserted = service.save(environment)

        then:
        inserted.id
    }

    def "Save throwing Exception on duplicate Name"() {
        Environment environment = new Environment()
        environment.setName("master-test-stage-1")

        when:
        service.save(environment)

        then:
        thrown(InvalidRequestException)
    }

    def "Delete"() {
        expect:
        service.delete('master-test-stage-1')
    }
}
