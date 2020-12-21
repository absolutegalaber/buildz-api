package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException

import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

class ServerServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    ServerService service

    @Unroll("#message")
    def "ByName"() {
        when:
        Optional<Server> server = service.byName(name)

        then:
        server.isPresent() == expected

        where:
        name            | expected | message
        "Doest exist"   | false    | 'ByName did not find non-existent Server'
        "Test Server 1" | true     | 'ByName found existing Server'
    }

    @Unroll("#message")
    def "TrackServer"() {
        when:
        Optional<Server> existingServer = service.byName(name)
        Server trackedServer = service.trackServer(name)

        then:
        if (existingServer.isPresent()) {
            // The Server already existed, so check to see if the trackedServer is the same as the
            // one in that already existed.
            assert existingServer.get() == trackedServer
        } else {
            // The Server did not exist before calling trackServer. Check to see if it has now been
            // created in the database.
            Optional<Server> newServer = service.byName(name)

            assert newServer.get() == trackedServer
        }

        where:
        name              | message
        "New Test Server" | 'TrackServer could not find existing Server so new one was created'
        "Test Server 1"   | 'TrackServer found an existing Server so a new one was not created'
    }

    @Unroll("#message")
    def "Fail to TrackServer"() {
        when:
        service.trackServer(name)

        then:
        thrown(InvalidRequestException)

        where:
        name | message
        null | 'Could not TrackServer with null name'
        ''   | 'Could not TrackServer with no name'
        ' '  | 'Could not TrackServer with empty name'
    }
}
