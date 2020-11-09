package com.absolutegalaber.buildz.api.fault

import com.absolutegalaber.buildz.domain.ExceptionInfo
import com.absolutegalaber.buildz.domain.exception.BuildzApiException
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class BuildzExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BuildzApiException)
    ResponseEntity<ExceptionInfo> handleConnversion(BuildzApiException ex) {
        ExceptionInfo body = new ExceptionInfo(
                key: ex.key,
                message: ex.getMessage(),
                description: ex.description
        )
        return new ResponseEntity<>(body, new HttpHeaders(), ex.status)
    }
}
