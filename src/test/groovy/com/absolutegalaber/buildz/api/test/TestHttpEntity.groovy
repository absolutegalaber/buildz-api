package com.absolutegalaber.buildz.api.test

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

class TestHttpEntity<T> extends HttpEntity<T> {
    private final static HEADERS = ["X-TenantName": "test"] as HttpHeaders

    TestHttpEntity() {
        super(null, HEADERS)
    }

    TestHttpEntity(T body) {
        super(body, HEADERS)
    }
}
