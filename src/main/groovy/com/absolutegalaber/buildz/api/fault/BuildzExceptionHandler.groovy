package com.absolutegalaber.buildz.api.fault

import com.absolutegalaber.buildz.domain.ExceptionInfo
import com.absolutegalaber.buildz.domain.exception.BuildzApiException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class BuildzExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BuildzApiException)
    ResponseEntity<ExceptionInfo> handleConversion(BuildzApiException ex) {
        ExceptionInfo body = new ExceptionInfo(
                key: ex.key,
                message: ex.getMessage(),
                description: ex.description
        )
        return new ResponseEntity<>(body, new HttpHeaders(), ex.status)
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionInfo body = new ExceptionInfo(
                key: 'invalid-input',
                message: ex.getMessage(),
                description: 'Input validation failed'
        )
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST)
    }
}
