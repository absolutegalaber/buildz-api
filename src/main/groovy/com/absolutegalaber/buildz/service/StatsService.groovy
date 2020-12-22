package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.BuildStats
import com.absolutegalaber.buildz.repository.BuildLabelRepository
import com.absolutegalaber.buildz.repository.BuildRepository
import com.absolutegalaber.buildz.repository.DeployRepository
import com.absolutegalaber.buildz.repository.EnvironmentRepository
import com.absolutegalaber.buildz.repository.ServerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StatsService {
    private final BuildRepository buildRepository
    private final BuildLabelRepository buildLabelRepository
    private final EnvironmentRepository environmentRepository
    private final ServerRepository serverRepository
    private final DeployRepository deployRepository

    StatsService(
            BuildRepository buildRepository,
            BuildLabelRepository buildLabelRepository,
            EnvironmentRepository environmentRepository,
            DeployRepository deployRepository
    ) {
        this.buildRepository = buildRepository
        this.buildLabelRepository = buildLabelRepository
        this.environmentRepository = environmentRepository
        this.deployRepository = deployRepository
    }

    BuildStats stats() {
        new BuildStats(
                numberOfBuilds: buildRepository.count(),
                numberOfLabels: buildLabelRepository.count(),
                numberOfDeploys: deployRepository.deployCount()
        )
    }
}
