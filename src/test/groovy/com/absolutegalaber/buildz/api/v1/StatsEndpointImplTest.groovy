package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.domain.BuildStats
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class StatsEndpointImplTest extends BaseRestSpec {
    def "Stats"() {
        given:
        String STATS_URL = "http://localhost:${port}/api/v1/stats"

        when:
        ResponseEntity<BuildStats> responseEntity = restTemplate.getForEntity(STATS_URL, BuildStats)

        then:
        responseEntity.statusCode == HttpStatus.OK
    }
}
