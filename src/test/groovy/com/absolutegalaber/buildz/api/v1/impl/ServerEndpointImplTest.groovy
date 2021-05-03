package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IServer
import com.absolutegalaber.buildz.api.test.TestHttpEntity
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.events.ReserveServerEvent
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ServerEndpointImplTest extends BaseRestSpec {
    def "Get"() {
        given:
        String serverName = 'Test-Server-1'
        String GET_SERVER_URL = "http://localhost:${port}/api/v1/servers/${serverName}"

        when:
        ResponseEntity<IServer> response = restTemplate.exchange(
                GET_SERVER_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                IServer
        )

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.body.id == 1
    }

    def "Get DataNotFound"() {
        given:
        String GET_SERVER_URL = "http://localhost:${port}/api/v1/servers/NoSuchServer"

        when:
        ResponseEntity<IServer> response = restTemplate.exchange(
                GET_SERVER_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                IServer
        )

        then:
        response.statusCode == HttpStatus.NOT_FOUND

    }

    def "List"() {
        given:
        String LIST_SERVERS_URL = "http://localhost:${port}/api/v1/servers/"

        when:
        ResponseEntity<List> response = restTemplate.exchange(
                LIST_SERVERS_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                List
        )

        then:
        response.statusCode == HttpStatus.OK

        and:
        !response.body.isEmpty()
    }

    def "Reserve Server"() {
        given:
        String RESERVE_SERVER_URL = "http://localhost:${port}/api/v1/servers/Test-Server-1/reservation"
        String by = "Endpoint Test"
        String note = "Testing"
        TestHttpEntity entity = new TestHttpEntity(new ReserveServerEvent(reservedBy: by, reservationNote: note))

        when:
        ResponseEntity<Server.Reservation> response = restTemplate
                .postForEntity(RESERVE_SERVER_URL, entity, Server.Reservation)

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.getBody() != null && response.getBody().by == by && response.getBody().note == note
    }

    def "Release Server"() {
        given:
        String RESERVE_SERVER_URL = "http://localhost:${port}/api/v1/servers/Test-Server-1/reservation"
        TestHttpEntity entity = new TestHttpEntity(new ReserveServerEvent(
                reservedBy: "Endpoint Test",
                reservationNote: "Testing"
        ))

        when:
        // First reserve a server...
        restTemplate.postForEntity(RESERVE_SERVER_URL, entity, Server.Reservation)
        // ... release it...
        restTemplate.exchange(RESERVE_SERVER_URL, HttpMethod.DELETE, new TestHttpEntity(), Void)
        // .. and finally get server to check for reservation
        ResponseEntity<Server> response = restTemplate.exchange(
                "http://localhost:${port}/api/v1/servers/Test-Server-1",
                HttpMethod.GET,
                new TestHttpEntity(),
                Server
        )

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.getBody().getReservation() == null
    }

    def "Update Server Info"() {
        given:
        String serverName = 'Test-Server-1'
        String nickName = 'SuperMachine'
        String description = 'My new super machine'
        String UPDATE_SERVER_URL = "http://localhost:${port}/api/v1/servers"
        def entity = new TestHttpEntity(new IServer(
                name: serverName,
                nickName: nickName,
                description: description,
        ))

        when:
        ResponseEntity<IServer> responseEntity = restTemplate.postForEntity(UPDATE_SERVER_URL, entity, IServer)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().getNickName() == nickName
        responseEntity.getBody().getDescription() == description
    }

    def "Invalid Update Server call"() {
        given:
        String nickName = 'SuperMachine'
        String description = 'My new super machine'
        String UPDATE_SERVER_URL = "http://localhost:${port}/api/v1/servers"
        def entity = new TestHttpEntity(new IServer(
                name: null,
                nickName: nickName,
                description: description,
        ))

        when:
        ResponseEntity<Object> response = restTemplate.postForEntity(UPDATE_SERVER_URL, entity, IServer)

        then:
        //Validation should kick in
        response.statusCode == HttpStatus.BAD_REQUEST
    }
}
