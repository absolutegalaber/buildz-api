package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.domain.BuildCount
import com.absolutegalaber.buildz.service.BuildCountService
import org.springframework.web.bind.annotation.*

import javax.transaction.Transactional

@Transactional
@RestController
class BuildNumberEndpoint {
    private final BuildCountService buildCountService

    BuildNumberEndpoint(BuildCountService buildCountService) {
        this.buildCountService = buildCountService
    }

    @PostMapping("/api/v1/build-numbers/next")
    BuildCount next(@RequestBody BuildCount buildCount) {
        return buildCountService.next(buildCount.getProject(), buildCount.getBranch())
    }

    @GetMapping('/api/v1/build-numbers/current/{project}/{branch}')
    BuildCount current(@PathVariable(name = 'project') String project, @PathVariable(name = 'branch') String branch) {
        return buildCountService.current(project, branch)
    }

    @PostMapping('/api/v1/build-numbers/set')
    BuildCount set(@RequestBody BuildCount buildCount) {
        buildCountService.set(buildCount.getProject(), buildCount.getBranch(), buildCount.getCounter())
    }
}
