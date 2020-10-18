package com.absolutegalaber.buildz

import com.absolutegalaber.buildz.api.v1.BuildNumberResourceV1
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
import javax.ws.rs.ApplicationPath

@Configuration
@ApplicationPath("/api")
class JerseyConfig extends ResourceConfig {
    @PostConstruct
    void init() {
        register(BuildNumberResourceV1.class)
    }

}
