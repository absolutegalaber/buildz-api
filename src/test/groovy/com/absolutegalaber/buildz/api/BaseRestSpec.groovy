package com.absolutegalaber.buildz.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql('/testdata.sql')
class BaseRestSpec extends Specification {
    @Autowired
    TestRestTemplate restTemplate
    @LocalServerPort
    Integer port
}
