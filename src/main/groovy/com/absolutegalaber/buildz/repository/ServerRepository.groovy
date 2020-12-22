package com.absolutegalaber.buildz.repository


import com.absolutegalaber.buildz.domain.Server
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * A Repository which handles {@link Server}s tracked by the Buildz System.
 */
interface ServerRepository extends PagingAndSortingRepository<Server, Long>, JpaSpecificationExecutor<Server> {
    @Query("SELECT s.name FROM Server s ORDER BY s.name ASC")
    List<String> distinctServers();

}
