package com.absolutegalaber.buildz.api.fault

import com.absolutegalaber.buildz.domain.ExceptionInfo
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import org.springframework.http.HttpStatus

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    @Override
    Response toResponse(DataNotFoundException e) {
        return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ExceptionInfo(
                key: 'not-found',
                description: 'No such data',
                message: e.getMessage()
        )).build()
    }
}
