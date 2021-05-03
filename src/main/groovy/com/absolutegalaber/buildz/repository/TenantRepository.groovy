package com.absolutegalaber.buildz.repository

import com.absolutegalaber.buildz.domain.Tenant
import org.springframework.data.repository.CrudRepository

interface TenantRepository extends CrudRepository<Tenant, String> {
}
