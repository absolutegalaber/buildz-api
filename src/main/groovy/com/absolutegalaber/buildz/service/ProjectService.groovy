package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.*
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException
import com.absolutegalaber.buildz.repository.BranchRepository
import com.absolutegalaber.buildz.repository.ProjectRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.*

@Service
@Transactional
class ProjectService {
    private final ProjectRepository projectRepository
    private final BranchRepository branchRepository
    private final BuildLabelService buildLabelService

    ProjectService(ProjectRepository projectRepository, BranchRepository branchRepository, BuildLabelService buildLabelService) {
        this.projectRepository = projectRepository
        this.branchRepository = branchRepository
        this.buildLabelService = buildLabelService
    }

    void trackProjectAndBranch(String projectName, String branchName) {
        Project project = projectRepository.findById(projectName).orElse(
                projectRepository.save(new Project(id: projectName))
        )
        Branch theBranch = branchRepository.findOne(ofProjectWithName(project, branchName)).orElse(
                new Branch(
                        project: project,
                        name: branchName
                )
        )
        if (!theBranch.id) {
            theBranch = branchRepository.save(theBranch)
            project.branches.add(theBranch)
        }
    }

    ProjectData dataForAllProjects(Boolean includeInactive = false) {
        //the data to return
        ProjectData toReturn = new ProjectData()
        //add all project names
        Iterable<Project> allProjects = projectRepository.findAll(
                allRelevantProjects(includeInactive),
                Sort.by('id')
        )
        toReturn.projects.addAll(
                allProjects.collect { new ProjectStatus(name: it.id, active: it.active) }
        )
        //add all branches per projec
        allProjects.each { Project project ->
            List<Branch> allBranchesProject = branchRepository.findAll(
                    ofProject(project, includeInactive), Sort.by('name')
            )
            toReturn.projectBranches.put(
                    project.id,
                    allBranchesProject.collect { new BranchStatus(name: it.name, active: it.active) }
            )
        }
        //add all Label keys
        toReturn.labelKeys.addAll(
                buildLabelService.allKnownLabelKeys()
        )
        toReturn
    }

    void setProjectOrBranchActivation(String projectName, String branchName, Boolean active) throws DataNotFoundException {
        Project theProject = projectRepository.findById(projectName).orElseThrow({ -> new DataNotFoundException("No project found for projectName=${projectName}") })
        if (branchName != null) {
            Branch theBranch = branchRepository.findOne(ofProjectWithName(theProject, branchName))
                    .orElseThrow({ -> new DataNotFoundException("No Branch found for branchName=${branchName}") })
            theBranch.active = active
            branchRepository.save(theBranch)
        } else {
            theProject.active = active
            projectRepository.save(theProject)
        }
    }
}
