package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
        name = 'ProjectInfo',
        description = 'Represents the minimum set of info needed for a Project: A name and a branch.'
)
@ToString(includes = ['projectName', 'branchName', 'active'])
@EqualsAndHashCode(includes = ['projectName', 'branchName', 'active'])
class ProjectBranchStatus implements Serializable {
    @Schema(required = true)
    String projectName

    @Schema(required = true)
    String branchName

    @Schema(required = true)
    Boolean active
}
