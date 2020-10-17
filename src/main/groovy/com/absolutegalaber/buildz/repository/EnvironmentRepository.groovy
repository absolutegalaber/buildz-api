package com.absolutegalaber.buildz.repository


import com.absolutegalaber.buildz.domain.Environment
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface EnvironmentRepository extends PagingAndSortingRepository<Environment, Long>, JpaSpecificationExecutor<Environment> {
    @Query("SELECT e.name FROM Environment e ORDER BY e.name ASC")
    Set<String> distinctEnvironments();

}