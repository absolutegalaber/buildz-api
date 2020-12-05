package com.absolutegalaber.buildz.repository


import com.absolutegalaber.buildz.domain.Project
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface ProjectRepository extends PagingAndSortingRepository<Project, String>, JpaSpecificationExecutor<Project> {
}