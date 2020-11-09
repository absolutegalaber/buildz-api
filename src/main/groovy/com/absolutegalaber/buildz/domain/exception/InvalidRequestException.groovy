package com.absolutegalaber.buildz.domain.exception

import org.springframework.http.HttpStatus

class InvalidRequestException extends BuildzApiException {
    InvalidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, 'invalid-request', 'Your request was not valid.')
    }
}
