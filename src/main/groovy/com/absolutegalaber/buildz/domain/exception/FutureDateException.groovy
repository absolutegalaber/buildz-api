package com.absolutegalaber.buildz.domain.exception

import org.springframework.http.HttpStatus

class FutureDateException extends BuildzApiException {
    FutureDateException(message) {
        super(message, HttpStatus.BAD_REQUEST, 'future-request', 'The provided at Date is in the future.')
    }
}
