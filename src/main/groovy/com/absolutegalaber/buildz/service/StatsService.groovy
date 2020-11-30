package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.BuildStats
import com.absolutegalaber.buildz.repository.BuildLabelRepository
import com.absolutegalaber.buildz.repository.BuildRepository
import com.absolutegalaber.buildz.repository.EnvironmentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StatsService {
    private final BuildRepository buildRepository
    private final BuildLabelRepository buildLabelRepository
    private final EnvironmentRepository environmentRepository

    StatsService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository, EnvironmentRepository environmentRepository) {
        this.buildRepository = buildRepository
        this.buildLabelRepository = buildLabelRepository
        this.environmentRepository = environmentRepository
    }

    BuildStats stats() {
        List<String> projects = buildRepository.distinctProjects()
        Map<String, List<String>> branchData = new HashMap<>()
        projects.each { String project ->
            branchData.put(project, buildRepository.distinctBranchesOf(project))
        }
        new BuildStats(
                projects: projects,
                projectBranches: branchData,
                environments: environmentRepository.distinctEnvironments() as Set<String>,
                labelKeys: buildLabelRepository.distinctLabelKeys(),
                numberOfBuilds: buildRepository.count(),
                numberOfLabels: buildLabelRepository.count()
        )
    }
}
