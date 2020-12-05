package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.ProjectData
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ProjectEndpointImplTest extends BaseRestSpec {
    def "GetProjectData"() {
        given:
        String PROJECT_DATA_URL = "http://localhost:${port}/api/v1/projects"

        when:
        ResponseEntity<ProjectData> responseEntity = restTemplate.getForEntity(PROJECT_DATA_URL, ProjectData)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.body.projects.size() == 3
    }

    def "GetProjectData Including Inactive"() {
        given:
        String PROJECT_DATA_URL = "http://localhost:${port}/api/v1/projects?include-inactive=true"

        when:
        ResponseEntity<ProjectData> responseEntity = restTemplate.getForEntity(PROJECT_DATA_URL, ProjectData)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.body.projects.size() == 4
    }
}
