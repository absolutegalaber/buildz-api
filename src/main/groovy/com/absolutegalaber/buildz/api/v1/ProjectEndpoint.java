package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.domain.ProjectBranchStatus;
import com.absolutegalaber.buildz.domain.ProjectData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "projects", description = "Provides Management of Projects and Branches thereof.")
public interface ProjectEndpoint {
    @Operation(
            summary = "Get data for all known projects.",
            description = "Get all Projects and their respective Branches and Labels.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project Data Loaded",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ProjectData")
                            )
                    )
            },
            tags = {"projects"}
    )
    @GetMapping("/api/v1/projects")
    ProjectData getProjectData(@RequestParam(name = "include-inactive", required = false, defaultValue = "false") Boolean includeInactive);

    @Operation(
            summary = "Set Project or Branch active",
            description = "Set active status of a Project or Branch",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Activation status set"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No Project and/or Branch founds",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                            )
                    )
            },
            tags = {"projects"}
    )
    @PostMapping("/api/v1/projects/project-branch-activa")
    void setProjectOrBranchActivation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ProjectBranchStatus")
                    )
            )
            @RequestBody ProjectBranchStatus projectBranch);
}
