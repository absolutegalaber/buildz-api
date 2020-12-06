package com.absolutegalaber.buildz.domain.exception


import org.springframework.http.HttpStatus

class DataNotFoundException extends BuildzApiException {
    DataNotFoundException(String message) {
        super(message,HttpStatus.NOT_FOUND, 'not-found', 'No such data')
    }
}
