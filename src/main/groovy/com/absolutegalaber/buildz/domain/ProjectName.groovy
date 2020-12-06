package com.absolutegalaber.buildz.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
        name = 'ProjectName',
        description = 'Represents the minimum set of info needed for a Project: A name.'
)
@ToString(includes = ['project'])
@EqualsAndHashCode(includes = ['project'])
class ProjectName implements Serializable {
    @Schema(required = true)
    String project
}
