package com.absolutegalaber.buildz.service

import com.absolutegalaber.buildz.BaseBuildzSpec
import com.absolutegalaber.buildz.domain.BuildCount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Subject
import spock.lang.Unroll

@SpringBootTest
class BuildCountServiceTest extends BaseBuildzSpec {
    @Subject
    @Autowired
    BuildCountService buildCountService

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
        project             | branch    | expected | message
        'buildz-backend'    | 'master'  | 2L       | "Next(): Increments existing build count"
        'buildz-backend'    | 'release' | 1L       | "Next(): Creates non-existing build count for new branch"
        'buildz-sub-module' | 'master'  | 1L       | "Next(): Creates non-existing build count for new project"

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
        project          | branch    | expected | message
        'buildz-backend' | 'master'  | 1L       | "Current(): returns current existing build count"
        'buildz-backend' | 'release' | 0L       | "Current(): return non-existing build count with count = 0"
    }

    @Unroll("#message")
    def "Set"() {
        when:
        BuildCount buildCount = buildCountService.set(project, branch, 10L)

        then:
        buildCount.counter == 10L

        where:
        project             | branch    | message
        'buildz-backend'    | 'master'  | "Set(): sets build number for existing build count"
        'buildz-sub-module' | 'release' | "Set(): creates build number for existing build count"
    }
}
