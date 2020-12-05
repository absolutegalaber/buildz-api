package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.Branch
import com.absolutegalaber.buildz.domain.Project
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

    def "trackProject() works for #projectName"() {
        when:
        Project project = service.trackProject(projectName)

        then:
        project.id

        where:
        projectName << ['backend', 'shiny-new-project']
    }

    def "trackBranchOf() works for #branchName of backend"() {
        given:
        String projectName = 'backend'

        when:
        Branch theBranch = service.trackBranchOf(projectName, branchName)

        then:
        theBranch.id

        where:
        branchName << ['main', 'feature/shiny-new-branch']
    }

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

    def "DataForAllProjects including inactive ones"() {
        when:
        ProjectData dataForAllProjects = service.dataForAllProjects(true)

        then:
        dataForAllProjects.projects.size() == 4

        and:
        dataForAllProjects.projectBranches.size() == 4

        and:
        dataForAllProjects.projectBranches.find { it.key == 'abandoned' }.value.size() == 2

        and:
        !dataForAllProjects.labelKeys.isEmpty()
    }
}
