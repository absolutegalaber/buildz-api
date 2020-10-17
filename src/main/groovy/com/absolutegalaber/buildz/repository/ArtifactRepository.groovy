package com.absolutegalaber.buildz.repository

import com.absolutegalaber.buildz.domain.Artifact
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface ArtifactRepository extends PagingAndSortingRepository<Artifact, Long>, JpaSpecificationExecutor<Artifact> {

}