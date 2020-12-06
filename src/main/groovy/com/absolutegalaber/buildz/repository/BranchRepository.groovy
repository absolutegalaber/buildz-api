package com.absolutegalaber.buildz.repository

import com.absolutegalaber.buildz.domain.Branch
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface BranchRepository extends PagingAndSortingRepository<Branch, String>, JpaSpecificationExecutor<Branch> {
}