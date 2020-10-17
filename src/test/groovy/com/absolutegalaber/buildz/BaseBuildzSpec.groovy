package com.absolutegalaber.buildz

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest
@ContextConfiguration
@Transactional
abstract class BaseBuildzSpec extends Specification {
}
