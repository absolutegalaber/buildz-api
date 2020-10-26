package com.absolutegalaber.buildz.repository


import com.absolutegalaber.buildz.domain.Build
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface BuildRepository extends PagingAndSortingRepository<Build, Long>, JpaSpecificationExecutor<Build> {
    @Query("SELECT DISTINCT b.project FROM Build b ORDER BY b.project ASC")
    Set<String> distinctProjects()

    @Query("SELECT DISTINCT b.branch FROM Build b ORDER BY b.branch ASC")
    Set<String> distinctBranches()

}