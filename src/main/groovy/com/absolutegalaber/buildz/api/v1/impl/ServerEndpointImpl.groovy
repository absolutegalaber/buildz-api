package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.model.IServer
import com.absolutegalaber.buildz.api.v1.ServerEndpoint
import com.absolutegalaber.buildz.domain.Server
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.events.ReserveServerEvent
import com.absolutegalaber.buildz.service.ServerService
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional
import java.nio.charset.StandardCharsets

/**
 * Implementation of {@link ServerEndpoint}
 */
@RestController
@Transactional
class ServerEndpointImpl implements ServerEndpoint {
    private final ServerService serverService

    ServerEndpointImpl(ServerService serverService) {
        this.serverService = serverService
    }

    @Override
    IServer get(String name) throws DataNotFoundException {
        String nameDecoded = URLDecoder.decode(name, StandardCharsets.UTF_8.name())
        IServer.of(
                serverService.byName(nameDecoded)
                        .orElseThrow({ -> new DataNotFoundException("No Server found with name=${nameDecoded}") })
        )
    }

    @Override
    List<IServer> list() {
        serverService.allServers().collect { IServer.of(it) }
    }

    @Override
    Server.Reservation reserveServer(String name, ReserveServerEvent event) {
        serverService.reserveServerByName(name, event)
    }

    @Override
    void releaseServer(String name) {
        serverService.releaseServerByName(name)
    }

    @Override
    IServer info(IServer server) throws DataNotFoundException {
        IServer.of(
                serverService.update(server.name, server.nickName, server.description)
        )
    }
}
