package com.absolutegalaber.buildz

import org.junit.Before
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest
@ContextConfiguration
@Transactional
@Sql(value = '/testdata.sql')
abstract class BaseBuildzSpec extends Specification {
}
