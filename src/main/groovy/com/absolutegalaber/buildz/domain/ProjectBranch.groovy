package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
        name = 'ProjectInfo',
        description = 'Represents the minimum set of info needed for a Project: A name and a branch.'
)
@ToString(includes = ['project', 'branch'])
@EqualsAndHashCode(includes = ['project', 'branch'])
class ProjectBranch implements Serializable {
    @Schema(required = true)
    String project

    @Schema(required = true)
    String branch
}
