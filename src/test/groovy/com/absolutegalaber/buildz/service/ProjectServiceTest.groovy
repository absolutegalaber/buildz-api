package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.ProjectData
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class ProjectServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    ProjectService service

    def "DataForAllProjects"() {
        when:
        ProjectData dataForAllProjects = service.dataForAllProjects()

        then:
        dataForAllProjects.projects.size() == 3

        and:
        dataForAllProjects.projectBranches.size() == 3

        and:
        dataForAllProjects.projectBranches.every { Map.Entry<String, List<String>> entry ->
            entry.value.size() == 3
        }

        and:
        !dataForAllProjects.labelKeys.isEmpty()
    }
}
