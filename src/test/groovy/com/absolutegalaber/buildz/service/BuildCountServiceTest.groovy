package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.BuildCount
import com.absolutegalaber.buildz.multitenancy.context.TenantContextStore
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.aop.target.ThreadLocalTargetSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.event.annotation.BeforeTestMethod
import spock.lang.Subject
import spock.lang.Unroll

@SpringBootTest
class BuildCountServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    BuildCountService buildCountService

    @MockBean
    ThreadLocalTargetSource threadLocalTenantStore



    @Unroll("#message")
    def "Next"() {
        when:
        BuildCount buildCount = buildCountService.next(project, branch)

        then:
        buildCount.counter == expected

        and:
        buildCount.project == project

        and:
        buildCount.branch == branch

        where:
        project             | branch                | expected | message
        'backend'           | 'main'                | 11L      | "Next(): Increments existing build count"
        //'backend'           | 'feature/new-feature' | 1L       | "Next(): Creates non-existing build count for new branch"
        //'buildz-sub-module' | 'master'              | 1L       | "Next(): Creates non-existing build count for new project"

    }

    @Unroll("#message")
    def "Current"() {
        when:
        BuildCount buildCount = buildCountService.current(project, branch)

        then:
        buildCount.counter == expected

        and:
        buildCount.project == project

        and:
        buildCount.branch == branch

        where:
        project   | branch    | expected | message
        'backend' | 'main'    | 10L      | "Current(): returns current existing build count"
        'backend' | 'release' | 0L       | "Current(): return non-existing build count with count = 0"
    }

    @Unroll("#message")
    def "Set"() {
        when:
        BuildCount buildCount = buildCountService.set(project, branch, 10L)

        then:
        buildCount.counter == 10L

        where:
        project             | branch    | message
        'buildz'            | 'master'  | "Set(): sets build number for existing build count"
        'buildz-sub-module' | 'release' | "Set(): creates build number for existing build count"
    }
}
