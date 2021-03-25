package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.events.ReserveServerEvent
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
        "Test-Server-1" | true     | 'ByName found existing Server'
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
        "New-Test-Server" | 'TrackServer could not find existing Server so new one was created'
        "Test-Server-1"   | 'TrackServer found an existing Server so a new one was not created'
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

    @Unroll("#message")
    def 'Valid Server reservation tests'() {
        when:
        def serverName = 'Test-Server-1'
        service.reserveServerByName(serverName, new ReserveServerEvent(reservedBy: by, reservationNote: note))

        then:
        Server.Reservation reservation = service.byName(serverName).get().getReservation()
        reservation != null && reservation.getBy() == by && reservation.getNote() == note

        where:
        by       | note   | message
        'Person' | null   | 'Was able to reserve the Server by "Person" (with a null note)'
        'Person' | ''     | 'Was able to reserve the Server by "Person" (with an unset note)'
        'Person' | ' '    | 'Was able to reserve the Server by "Person" (with an empty note)'
        'Person' | 'note' | 'Was able to reserve the Server by "Person" (with a note)'
    }

    @Unroll("#message")
    def 'Invalid Server reservation tests'() {
        when:
        // The reservation note should not affect the reservation logic, so ignore it for these tests
        service.reserveServerByName(serverName, new ReserveServerEvent(reservedBy: by))

        then:
        thrown(exception)

        where:
        serverName      | by       | exception               | message
        null            | null     | InvalidRequestException | 'Could not reserve Server with null name (by null)'
        null            | ''       | InvalidRequestException | 'Could not reserve Server with null name (by "")'
        null            | 'Person' | InvalidRequestException | 'Could not reserve Server with null name (by "Person")'
        ''              | null     | InvalidRequestException | 'Could not reserve Server with no name (by null)'
        ''              | ''       | InvalidRequestException | 'Could not reserve Server with no name (by "")'
        ''              | 'Person' | InvalidRequestException | 'Could not reserve Server with no name (by "Person")'
        ' '             | null     | InvalidRequestException | 'Could not reserve Server with empty name (by null)'
        ' '             | ''       | InvalidRequestException | 'Could not reserve Server with empty name (by "")'
        ' '             | 'Person' | InvalidRequestException | 'Could not reserve Server with empty name (by "Person")'
        'Fake-Server'   | null     | DataNotFoundException   | 'Could not reserve Server with no name (by null)'
        'Fake-Server'   | ''       | DataNotFoundException   | 'Could not reserve Server with invalid name (by "")'
        'Fake-Server'   | 'Person' | DataNotFoundException   | 'Could not reserve Server with invalid name (by "Person")'
        'Test-Server-1' | null     | InvalidRequestException | 'Could not reserve Server with valid name (by null)'
        'Test-Server-1' | ''       | InvalidRequestException | 'Could not reserve Server with valid name (by "")'
        'Test-Server-1' | ' '      | InvalidRequestException | 'Could not reserve Server with valid name (by " ")'
    }

    // NOTE: No need to test the ServerService#reserveServer method since it is already tested by the
    // ServerService#reserveServerByName tests

    @Unroll("#message")
    def 'Valid ReleaseServer tests'() {
        given:
        def serverName = 'Test-Server-1'

        when:
        if (withReservation) {
            service.reserveServerByName(
                    serverName,
                    new ReserveServerEvent(
                            reservedBy: 'Splunk',
                            reservationNote: 'Testing'
                    )
            )
        }
        service.releaseServerByName(serverName)

        then:
        Server server = service.byName(serverName).get()

        !server.getReservation()

        where:
        withReservation | message
        true            | 'Was able to release a Server with a reservation'
        false           | 'Was able to release a Server with no reservation'
    }

    @Unroll("#message")
    def 'Invalid ReleaseServer tests'() {

        when:
        service.releaseServerByName(serverName)

        then:
        thrown(exception)

        where:
        serverName    | exception               | message
        null          | InvalidRequestException | 'Was unable to release Server because serverName was null'
        ''            | InvalidRequestException | 'Was unable to release Server because serverName was not set'
        ' '           | InvalidRequestException | 'Was unable to release Server because serverName was empty'
        'Fake-Server' | DataNotFoundException   | 'Was unable to release Server because a Server with the provided Server name does not exist'
    }


    @Unroll("#message")
    def "Valid Server info"() {

        when:
        Server updated = service.update(serverName, nickName, description)

        then:
        updated.name == serverName
        and:
        updated.nickName == nickName
        and:
        updated.description == description

        where:
        serverName      | nickName      | description
        'Test-Server-1' | 'My Precious' | 'My Cool Test Server #1'
        'Test-Server-1' | null          | null
    }

    @Unroll("Update() throws #exceptionClass for serverName=#serverName")
    def "Invalid Server Info"() {

        when:
        service.update(serverName, nickName, description)

        then:
        thrown(exceptionClass)

        where:
        serverName     | nickName      | description | exceptionClass
        'NoSuchServer' | 'My Precious' | 'My Cool Test Server #1' | DataNotFoundException
        null           | 'My Precious' | 'My Cool Test Server #1' | NullPointerException
    }
}
