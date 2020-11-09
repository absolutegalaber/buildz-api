package com.absolutegalaber.buildz.domain.exception

import org.springframework.http.HttpStatus

abstract class BuildzApiException extends Exception {
    private final HttpStatus status
    private final String key
    private final String description

    BuildzApiException(String message, HttpStatus status, String key, String description) {
        super(message)
        this.status = status
        this.key = key
        this.description = description
    }

    HttpStatus getStatus() {
        return status
    }

    String getKey() {
        return key
    }

    String getDescription() {
        return description
    }
}
