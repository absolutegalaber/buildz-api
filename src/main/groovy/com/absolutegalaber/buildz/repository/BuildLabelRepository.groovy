package com.absolutegalaber.buildz.repository


import com.absolutegalaber.buildz.domain.BuildLabel
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface BuildLabelRepository extends PagingAndSortingRepository<BuildLabel, Long>, JpaSpecificationExecutor<BuildLabel> {
    @Query("SELECT DISTINCT l.key FROM BuildLabel l")
    Set<String> distinctLabelKeys()
}