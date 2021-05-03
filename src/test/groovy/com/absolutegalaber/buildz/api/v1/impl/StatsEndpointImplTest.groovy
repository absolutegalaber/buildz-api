package com.absolutegalaber.buildz.api.v1.impl

import com.absolutegalaber.buildz.api.BaseRestSpec
import com.absolutegalaber.buildz.api.test.TestHttpEntity
import com.absolutegalaber.buildz.domain.BuildStats
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class StatsEndpointImplTest extends BaseRestSpec {
    def "Stats"() {
        given:
        String STATS_URL = "http://localhost:${port}/api/v1/stats"

        when:
        ResponseEntity<BuildStats> responseEntity = restTemplate.exchange(
                STATS_URL,
                HttpMethod.GET,
                new TestHttpEntity(),
                BuildStats
        )

        then:
        responseEntity.statusCode == HttpStatus.OK
    }
}
