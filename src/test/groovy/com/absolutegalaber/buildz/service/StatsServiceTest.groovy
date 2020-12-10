package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.BuildStats
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class StatsServiceTest extends BaseBuildzSpec {

    @Subject
    @Autowired
    StatsService statesService

    def "Stats"() {
        when:
        BuildStats stats = statesService.stats()

        then:
        stats.numberOfBuilds > 0
    }
}
