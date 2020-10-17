package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.domain.BuildStats
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class StatsServiceTest extends com.absolutegalaber.buildz.BaseBuildzSpec {

    @Subject
    @Autowired
    StatsService statesService;

    def "Stats"() {
        when:
        BuildStats stats = statesService.stats()

        then:
        stats.numberOfBuilds > 0

        and:
        stats.projects.size() > 0

        and:
        stats.environments.size() > 0
    }
}
