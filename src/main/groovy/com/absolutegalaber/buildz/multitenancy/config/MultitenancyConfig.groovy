package com.absolutegalaber.buildz.multitenancy.config

import com.absolutegalaber.buildz.multitenancy.routing.TenantBasedRoutingDataSource
import com.absolutegalaber.buildz.service.TenantService
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.Location
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.event.EventListener
import org.springframework.core.env.Environment

import javax.sql.DataSource

@Configuration
@Conditional(MultitenancyEnabledCondition.class)
class MultitenancyConfig {

    @Autowired
    private ApplicationContext context

    @Autowired
    private Environment env

    private TenantBasedRoutingDataSource routingDataSource

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSourceProperties primaryDataSourceProperties() {
        new DataSourceProperties()
    }

    @Bean
    @Primary
    DataSource dataSource() {
        this.routingDataSource = new TenantBasedRoutingDataSource()

        final Map<String, DataSource> dataSources = new HashMap<>()

        updateRoutingDataSource(dataSources)

        this.routingDataSource
    }

    @EventListener(ApplicationReadyEvent.class)
    void prepareTenantDataSources() {
        TenantService tenantService = this.context.getBean("tenantService")

        if (env.getProperty("multitenancy.flyway.enabled", Boolean.class, false)) {
            Flyway
                    .configure()
                    .dataSource(routingDataSource.getResolvedDefaultDataSource())
                    .locations(new Location("db/migration/multitenancy"))
                    .load()
                    .migrate()

        }

        updateRoutingDataSource(tenantService.fetchTenantDataSources())

        if (env.getProperty("multitenancy.flyway.enabled", Boolean.class, false)) {
            this.routingDataSource.getResolvedDataSources().each {
                Flyway
                        .configure()
                        .locations("db/migration/tenant")
                        .dataSource(it.value)
                        .load()
                        .migrate()
            }
        }
    }

    private void updateRoutingDataSource(Map<String, DataSource> dataSources) {
        def defaultDataSource = primaryDataSourceProperties()
                .initializeDataSourceBuilder()
                .build()

        this.routingDataSource.setTargetDataSources(dataSources)
        this.routingDataSource.setDefaultTargetDataSource(defaultDataSource)
        this.routingDataSource.afterPropertiesSet()
    }
}
