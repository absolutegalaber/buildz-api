package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.model.IServer
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.events.ReserveServerEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ServerEndpointImplTest extends BaseRestSpec {
    def "Get"() {
        given:
        String serverName = 'Test-Server-1'
        String GET_SERVER_URKL = "http://localhost:${port}/api/v1/servers/${serverName}"

        when:
        ResponseEntity<IServer> response = restTemplate.getForEntity(GET_SERVER_URKL, IServer)

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.body.id == 1
    }

    def "Get DataNotFound"() {
        given:
        String GET_SERVER_URKL = "http://localhost:${port}/api/v1/servers/NoSuchServer"

        when:
        ResponseEntity<IServer> response = restTemplate.getForEntity(GET_SERVER_URKL, IServer)

        then:
        response.statusCode == HttpStatus.NOT_FOUND

    }

    def "List"() {
        given:
        String LIST_SERVERS_URKL = "http://localhost:${port}/api/v1/servers/"

        when:
        ResponseEntity<List> response = restTemplate.getForEntity(LIST_SERVERS_URKL, List)

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
        ReserveServerEvent reservation = new ReserveServerEvent(reservedBy: by, reservationNote: note)

        when:
        ResponseEntity<Server.Reservation> response = restTemplate
                .postForEntity(RESERVE_SERVER_URL, reservation, Server.Reservation)

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.getBody() != null && response.getBody().by == by && response.getBody().note == note
    }

    def "Release Server"() {
        given:
        String RESERVE_SERVER_URL = "http://localhost:${port}/api/v1/servers/Test-Server-1/reservation"
        ReserveServerEvent reservation = new ReserveServerEvent(
                reservedBy: "Endpoint Test",
                reservationNote: "Testing"
        )

        when:
        // First reserve a server...
        restTemplate.postForEntity(RESERVE_SERVER_URL, reservation, Server.Reservation)
        // ... release it...
        restTemplate.delete(RESERVE_SERVER_URL)
        // .. and finally get server to check for reservation
        ResponseEntity<Server> response =
                restTemplate.getForEntity("http://localhost:${port}/api/v1/servers/Test-Server-1", Server)

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
        def theServer = new IServer(
                name: serverName,
                nickName: nickName,
                description: description,
        )

        when:
        ResponseEntity<IServer> responseEntity = restTemplate.postForEntity(UPDATE_SERVER_URL, theServer, IServer)

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
        def theServer = new IServer(
                name: null,
                nickName: nickName,
                description: description,
        )

        when:
        ResponseEntity<Object> response = restTemplate.postForEntity(UPDATE_SERVER_URL, theServer, IServer)

        then:
        //Validation should kick in
        response.statusCode == HttpStatus.BAD_REQUEST
    }
}
