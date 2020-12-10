package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.BranchStatus
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
        dataForAllProjects.projectBranches.every { Map.Entry<String, List<BranchStatus>> entry ->
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


    @Unroll("setProjectOrBranchActivation(): #message")
    def "toggleProjectActive"() {
        when:
        service.setProjectOrBranchActivation(projectName, branchName, active)

        then:
        notThrown(Exception)
        where:
        projectName | branchName | active | message
        'backend'   | 'main'     | false  | 'works for active branch'
        'backend'   | null       | false  | 'works for active project'
    }

    @Unroll("setProjectOrBranchActivation() throws DataNotFOundExecption for wrong #type")
    def "toggleProjectActive throwing Exception"() {
        when:
        service.setProjectOrBranchActivation(projectName, branchName, active)

        then:
        thrown(DataNotFoundException)
        where:
        projectName | branchName       | active | type
        'wrongNAme' | 'main'           | false  | 'projectName'
        'backend'   | 'no-such-branch' | false  | 'branchName'
    }


}
