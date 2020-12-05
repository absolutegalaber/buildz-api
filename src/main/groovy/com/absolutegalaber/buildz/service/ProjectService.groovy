package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.Branch
import com.absolutegalaber.buildz.domain.Project
import com.absolutegalaber.buildz.domain.ProjectData
import com.absolutegalaber.buildz.repository.BranchRepository
import com.absolutegalaber.buildz.repository.ProjectRepository
import com.absolutegalaber.buildz.repository.QuerySpecs
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    ProjectData dataForAllProjects() {
        //the data to return
        ProjectData toReturn = new ProjectData()
        //add all project names
        Iterable<Project> allProjects = projectRepository.findAll(Sort.by('id'))
        toReturn.projects.addAll(
                allProjects.collect { it.id }
        )
        //add all branches per projec
        allProjects.each { Project project ->
            List<Branch> allBranchesProject = branchRepository.findAll(QuerySpecs.ofProject(project), Sort.by('name'))
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
