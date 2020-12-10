package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.domain.BuildCount;
import com.absolutegalaber.buildz.domain.ProjectBranch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "build-numbers", description = "Provides Build Numbers in a coherent way.")
public interface BuildNumberEndpoint {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Provides the next BuildCount containing a sensible next Build Number.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/BuildCount")
                    )
            )
    })
    @Operation(summary = "Get new BuildNumber", description = "Increment the Build Counter and Return the new BuildCount", tags = {"build-numbers"})
    @PostMapping("/api/v1/build-numbers/next")
    BuildCount next(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ProjectInfo")
                    )
            )
            @RequestBody ProjectBranch projectBranch
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/BuildCount")
                    )
            )
    })
    @Operation(summary = "Get current BuildNumber", description = "Return the current BuildCount", tags = {"build-numbers"})
    @GetMapping("/api/v1/build-numbers/current/{project}/{branch}")
    BuildCount current(
            @Parameter(name = "project", description = "Name of the project to get the current BuildCount for")
            @PathVariable(name = "project")
                    String project,
            @Parameter(name = "branch", description = "Branch of the project to get the current BuildCount for")
            @PathVariable(name = "branch")
                    String branch
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/BuildCount")
                    )
            )
    })
    @Operation(summary = "Set current BuildNumber", description = "Set and return the current BuildCount", tags = {"build-numbers"})
    @PostMapping("/api/v1/build-numbers/set")
    BuildCount set(@RequestBody BuildCount buildCount);
}
