package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.domain.ProjectData;
import com.absolutegalaber.buildz.domain.ProjectInfo;
import com.absolutegalaber.buildz.domain.ProjectName;
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
            summary = "Toggle active status of a Branch",
            description = "Toggle active status of a Branch",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Toggled"
                    )
            },
            tags = {"projects"}
    )
    @PostMapping("/api/v1/projects/toggle-branch-active")
    void toggleBranchActivation(@RequestBody ProjectInfo projectInfo);

    @Operation(
            summary = "Toggle active status of a Project",
            description = "Toggle active status of a Project",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Toggled"
                    )
            },
            tags = {"projects"}
    )
    @PostMapping("/api/v1/projects/toggle-project-active")
    void toggleProjectActivation(@RequestBody ProjectName projectName);
}
