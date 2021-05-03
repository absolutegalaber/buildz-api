package com.absolutegalaber.buildz.multitenancy.exceptions

import com.absolutegalaber.buildz.domain.exception.BuildzApiException
import org.springframework.http.HttpStatus

class MissingTenantNameHeaderException extends BuildzApiException {
    MissingTenantNameHeaderException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, 'missing-tenant-name', 'Missing Tenant Name header')
    }
}
