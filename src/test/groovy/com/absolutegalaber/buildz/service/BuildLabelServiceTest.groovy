package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildLabelServiceTest extends BaseBuildzSpec {
    @Autowired
    BuildLabelService buildLabelService


    def "AllKnownLabelKeys"() {
        expect:
        !buildLabelService.allKnownLabelKeys().isEmpty()
    }
}
