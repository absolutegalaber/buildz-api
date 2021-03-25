package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Server
import groovy.transform.EqualsAndHashCode

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@EqualsAndHashCode(includes = ['id', 'name'])
class IServer implements Serializable {
    Long id
    @NotNull(message = "Server.name can not be empty")
    @NotEmpty(message = "Server.name can not be empty")
    String name
    String nickName
    String description
    IReservation reservation

    static IServer of(Server server) {
        new IServer(
                id: server.id,
                name: server.name,
                nickName: server.nickName,
                description: server.description,
                reservation: IReservation.by(server.reservation)

        )
    }
}
