package com.absolutegalaber.buildz.api.v1

import com.absolutegalaber.buildz.domain.BuildCount
import com.absolutegalaber.buildz.service.BuildCountService
import org.springframework.stereotype.Component

import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Component
@Path('v1/build-numbers')
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
class BuildNumberResourceV1 {
    private final BuildCountService buildCountService

    BuildNumberResourceV1(BuildCountService buildCountService) {
        this.buildCountService = buildCountService
    }

    @POST
    @Path("/next")
    BuildCount next(BuildCount buildCount) {
        return buildCountService.next(buildCount.getProject(), buildCount.getBranch())
    }

    @GET
    @Path("/current/{project}/{branch}")
    BuildCount current(@PathParam("project") String project, @PathParam("branch") String branch) {
        return buildCountService.current(project, branch)
    }

    @POST
    @Path("/set")
    BuildCount set(BuildCount buildCount) {
        buildCountService.set(buildCount.getProject(), buildCount.getBranch(), buildCount.getCounter())
    }
}
