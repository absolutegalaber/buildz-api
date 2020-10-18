package com.absolutegalaber.buildz.api.fault

import com.absolutegalaber.buildz.domain.ExceptionInfo
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import org.springframework.http.HttpStatus

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidRequestException> {
    @Override
    Response toResponse(InvalidRequestException e) {
        return Response.status(HttpStatus.BAD_REQUEST.value()).entity(new ExceptionInfo(
                key: 'invalid-request',
                description: 'Your request was not valid.',
                message: e.getMessage()
        )).build()
    }
}
