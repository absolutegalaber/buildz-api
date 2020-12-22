package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.Server
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ServerEndpointImplTest extends BaseRestSpec {
    def "Get"() {
        given:
        String serverName = 'Test Server 1'
        String GET_SERVER_URKL = "http://localhost:${port}/api/v1/servers/${serverName}"

        when:
        ResponseEntity<Server> response = restTemplate.getForEntity(GET_SERVER_URKL, Server)

        then:
        response.statusCode == HttpStatus.OK

        and:
        response.body.id == 1
    }

    def "Get DataNotFound"() {
        given:
        String GET_SERVER_URKL = "http://localhost:${port}/api/v1/servers/NoSuchServer"

        when:
        ResponseEntity<Server> response = restTemplate.getForEntity(GET_SERVER_URKL, Server)

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
}
