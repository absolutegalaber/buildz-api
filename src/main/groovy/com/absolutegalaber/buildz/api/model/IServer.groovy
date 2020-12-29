package com.absolutegalaber.buildz.api.model

import com.absolutegalaber.buildz.domain.Server
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['id', 'name'])
class IServer implements Serializable {
    Long id
    String name

    static IServer of(Server server) {
        new IServer(
                id: server.id,
                name: server.name
        )
    }
}
