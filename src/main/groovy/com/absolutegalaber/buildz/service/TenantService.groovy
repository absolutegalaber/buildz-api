package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.Tenant
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException
import com.absolutegalaber.buildz.repository.TenantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.task.TaskDecorator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Service("tenantService")
@Transactional
class TenantService {

    @Autowired
    private TenantRepository tenantRepository

    Map<String, DataSource> tenantDataSources

    Map<String, DataSource> fetchTenantDataSources() {
        if (tenantDataSources != null) {
            return tenantDataSources
        }

        tenantDataSources = tenantRepository
                .findAll()
                .collectEntries {[it.tenantName, it.buildDataSource()]}

        tenantDataSources
    }

    void addTenant(String tenantName) throws InvalidRequestException {
        // TODO add support for other drivers, hosts, ports, and (maybe?) custom parameters
        def url = "jdbc:mysql:localhost:3306/${UUID.randomUUID()}?createDatabaseIfNotExist=true"
        Tenant tenant = new Tenant(
                tenantName: tenantName,
                url: url,
                driverClassName: "com.mysql.cj.jdbc.Driver", // TODO add support for other drivers
                username: "buildz", // TODO generate random username
                password: "buildz", // TODO generate random username
        )

        tenantRepository.save(tenant)
    }

    // TODOUpdate, and Delete logic
    // the Creation and Update logic will create a new tenant DB and a row in the tenant table. The DB creation will
    // most likely need to be done via a Query call or creating a DataSource with the appropriate Hibernate flag (maybe
    // createDatabaseIfNotExist=true in the URL with hibernate.hbm2ddl.auto=create in the properties)?
    // The delete logic should remove the database, remove the tenant row, and maybe create a backup for the user?
}
