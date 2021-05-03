package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.multitenancy.context.TenantContextStore
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestServiceConfig {

    @Bean
    TenantContextStore testTenantContextStore() {
        def store = new TenantContextStore()

        store.tenantUUID = "test"

        store
    }
}
