package com.absolutegalaber.buildz.repository


import com.absolutegalaber.buildz.domain.BuildCount
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface BuildCountRepository extends PagingAndSortingRepository<BuildCount, Long>, JpaSpecificationExecutor<BuildCount> {

}