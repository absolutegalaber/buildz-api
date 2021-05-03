package com.absolutegalaber.buildz.multitenancy.routing


import com.absolutegalaber.buildz.multitenancy.context.TenantContextStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

/**
 * A Datasource which routes to a specific database based on the
 * current Thread's "Tenant Name".
 */
class TenantBasedRoutingDataSource extends AbstractRoutingDataSource {

    @Autowired
    TenantContextStore tenantStore

    @Override
    protected Object determineCurrentLookupKey() {
        println "found ${tenantStore.getTenantUUID()} in ${tenantStore}"
        tenantStore.getTenantUUID()
    }
}
