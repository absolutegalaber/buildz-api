package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.Branch
import com.absolutegalaber.buildz.domain.Project
import com.absolutegalaber.buildz.domain.ProjectData
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by Josip.Mihelko @ Gmail
 */
class ProjectServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    ProjectService service

    @Unroll("trackBranchOf(): #message")
    def "trackBranchOf() works for #branchName of backend"() {
        when:
        service.trackProjectAndBranch(projectName, branchName)

        then:
        notThrown()

        where:
        projectName        | branchName         | message
        'cool-new-project' | 'main'             | 'Works for new Poject'
        'backend'          | 'feature/cool-new' | 'Works for existing Project and new Branch'
        'backend'          | 'main'             | 'Works for existing Project and existing Branch'
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


    def "toggleProjectActive"() {
        when:
        Project updated = service.toggleProjectActive('backend')

        then:
        !updated.active
    }

    def "toggleProjectActive with wrong project"() {
        when:
        service.toggleProjectActive('missing')

        then:
        thrown(DataNotFoundException)
    }

    def "toggleProjectBranch"() {
        when:
        Branch updated = service.toggleBranchActive('backend', 'main')

        then:
        !updated.active
    }

    def "toggleProjectBranch with wrong project"() {
        when:
        service.toggleBranchActive('missing', 'main')

        then:
        thrown(DataNotFoundException)
    }

    def "toggleProjectBranch with wrong branch"() {
        when:
        service.toggleBranchActive('backend', 'missing')

        then:
        thrown(DataNotFoundException)
    }
}
