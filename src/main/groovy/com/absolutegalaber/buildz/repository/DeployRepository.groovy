package com.absolutegalaber.buildz.repository

import com.absolutegalaber.buildz.domain.Deploy
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * A Repository which handles {@link Deploy}s tracked by the Buildz System.
 */
interface DeployRepository extends PagingAndSortingRepository<Deploy, Long>, JpaSpecificationExecutor<Deploy> {
    @Query("SELECT COUNT(d) FROM Deploy d")
    long deployCount();

}
