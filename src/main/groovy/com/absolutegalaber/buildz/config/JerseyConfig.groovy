package com.absolutegalaber.buildz.config

import com.absolutegalaber.buildz.api.fault.DataNotFoundExceptionMapper
import com.absolutegalaber.buildz.api.fault.InvalidRequestExceptionMapper
import com.absolutegalaber.buildz.api.v1.BuildNumberEndpoint
import com.absolutegalaber.buildz.api.v1.BuildEndpoint
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.ServerProperties
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
import javax.ws.rs.ApplicationPath

@Configuration
@ApplicationPath("/api")
class JerseyConfig extends ResourceConfig {
    @PostConstruct
    void init() {
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, false);
        register(BuildEndpoint.class)
        register(BuildNumberEndpoint.class)
        register(InvalidRequestExceptionMapper.class)
        register(DataNotFoundExceptionMapper.class)
    }

}
