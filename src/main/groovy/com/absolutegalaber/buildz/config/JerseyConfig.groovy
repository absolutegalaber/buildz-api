package com.absolutegalaber.buildz.config

import com.absolutegalaber.buildz.api.fault.DataNotFoundExceptionMapper
import com.absolutegalaber.buildz.api.fault.InvalidRequestExceptionMapper
import com.absolutegalaber.buildz.api.v1.BuildEndpoint

import com.absolutegalaber.buildz.api.v1.BuildNumberEndpoint
import com.absolutegalaber.buildz.api.v1.EnvironmentEndpoint
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
import javax.ws.rs.ApplicationPath

@Configuration
@ApplicationPath("/api")
class JerseyConfig extends ResourceConfig {
    @PostConstruct
    void init() {
        register(BuildEndpoint)
        register(BuildNumberEndpoint)
        register(EnvironmentEndpoint)
        register(InvalidRequestExceptionMapper)
        register(DataNotFoundExceptionMapper)
    }

}
