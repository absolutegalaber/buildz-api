package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.api.model.IArtifact
import com.absolutegalaber.buildz.api.model.IEnvironment
import com.absolutegalaber.buildz.domain.Environment
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
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
        name      | expected | message
        'main'    | true     | 'ByName(): finds existing Environment by name'
        'missing' | false    | 'ByName(): is empty for non-existing environment name'
    }

    def "Save inserting new instance"() {
        given:
        IEnvironment environment = new IEnvironment()
        environment.setName("new-environment")
        IArtifact artifact = new IArtifact(
                project: 'some-project',
                branch: 'some-branch'
        )
        artifact.getLabels().put('some', 'label')
        environment.getArtifacts().add(artifact)

        when:
        Environment inserted = service.save(environment, false)

        then:
        inserted.id

        and:
        inserted.artifacts.size() == 1

        and:
        !inserted.artifacts.first().labels.isEmpty()
    }


    def "Save updating existing instance"() {
        given:
        IEnvironment environment = new IEnvironment(
                id: 1,
                name: 'main'
        )
        IArtifact artifact = new IArtifact(
                project: 'some-project',
                branch: 'some-branch'
        )
        artifact.getLabels().put('some', 'label')
        environment.getArtifacts().add(artifact)


        when:
        Environment inserted = service.save(environment, false)

        then:
        inserted.id
    }

    def "Save throwing Exception on duplicate Name"() {
        IEnvironment environment = new IEnvironment()
        environment.setName("main")

        when:
        service.save(environment, false)

        then:
        thrown(InvalidRequestException)
    }

    def "Delete"() {
        expect:
        service.delete('main')
    }

    def "List"() {
        expect:
        !service.allEnvironments().isEmpty()
    }

    def "Attempts to update internal Environment with non-internal request"() {
        IEnvironment environment = new IEnvironment()
        environment.setName("testing")
        Environment savedEnv = service.save(environment, true)

        environment.setId(savedEnv.id)

        when:
        environment.setName("testing (update)")
        service.save(environment, false)

        then:
        thrown(InvalidRequestException)
    }
}
