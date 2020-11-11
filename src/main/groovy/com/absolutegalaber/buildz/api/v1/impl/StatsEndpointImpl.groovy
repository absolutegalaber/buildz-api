package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.v1.StatsEndpoint
import com.absolutegalaber.buildz.domain.BuildStats
import com.absolutegalaber.buildz.service.StatsService
import org.springframework.web.bind.annotation.RestController

import javax.transaction.Transactional

@RestController
@Transactional
class StatsEndpointImpl implements StatsEndpoint {
    private final StatsService statsService

    StatsEndpointImpl(StatsService statsService) {
        this.statsService = statsService
    }

    @Override
    BuildStats stats() {
        statsService.stats()
    }
}
