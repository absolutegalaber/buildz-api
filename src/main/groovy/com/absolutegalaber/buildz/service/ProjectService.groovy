package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.Branch
import com.absolutegalaber.buildz.domain.Project
import com.absolutegalaber.buildz.domain.ProjectData
import com.absolutegalaber.buildz.repository.BranchRepository
import com.absolutegalaber.buildz.repository.ProjectRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import static com.absolutegalaber.buildz.repository.QuerySpecs.allRelevantProjects
import static com.absolutegalaber.buildz.repository.QuerySpecs.ofProject

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

    ProjectData dataForAllProjects(Boolean includeInactive = false) {
        //the data to return
        ProjectData toReturn = new ProjectData()
        //add all project names
        Iterable<Project> allProjects = projectRepository.findAll(
                allRelevantProjects(includeInactive),
                Sort.by('id')
        )
        toReturn.projects.addAll(
                allProjects.collect { it.id }
        )
        //add all branches per projec
        allProjects.each { Project project ->
            List<Branch> allBranchesProject = branchRepository.findAll(
                    ofProject(project, includeInactive), Sort.by('name')
            )
            toReturn.projectBranches.put(
                    project.id,
                    allBranchesProject.collect { it.name }
            )
        }
        //add all Label keys
        toReturn.labelKeys.addAll(
                buildLabelService.allKnownLabelKeys()
        )
        toReturn
    }
}
