package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.domain.Artifact;
import com.absolutegalaber.buildz.domain.Environment;
import com.absolutegalaber.buildz.domain.EnvironmentBuilds;
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException;
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "environments", description = "Provides Management of Environments.")
public interface EnvironmentEndpoint {

    @Operation(
            summary = "Create / Update an Environment,",
            description = "Create / Update an Environment,",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Environment")
                    ),
                    description = "The Environment to update/create.",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Environment Saved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Environment")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Environment Not Saved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                            )
                    )
            },
            tags = "environments"
    )
    @PostMapping(value = "/api/v1/environments")
    Environment save(@RequestBody Environment environment) throws InvalidRequestException;

    @Operation(
            summary = "Load an Environment,",
            description = "Loads a specific Environment by name,",
            parameters = {
                    @Parameter(name = "name", description = "Name of a defined Environment")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Environment Loaded",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Environment")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No Environment with this name was found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                            )
                    )
            },
            tags = "environments"
    )
    @GetMapping("/api/v1/environments/{name}")
    Environment get(
            @Parameter(name = "name", description = "Name of the configured Environment.")
            @PathVariable("name") String name
    ) throws DataNotFoundException;

    @Operation(
            summary = "Verify Artefacts,",
            description = "Searches for Builds according to the set of Artifacts provided.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = Artifact.class), array = @ArraySchema)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Verification results returned.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/EnvironmentBuilds")
                            )
                    )
            },
            tags = "environments"
    )
    @PostMapping("/api/v1/environments/verify-artifacts")
    EnvironmentBuilds verifyEnvironment(@RequestBody Set<Artifact> artifacts);

    @Operation(
            summary = "Deletes an Environment",
            description = "Deletes an Environment",
            parameters = {
                    @Parameter(name = "name", description = "NAme of a defined Environment")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Environment deleted if it was found."
                    )
            },
            tags = "environments"
    )
    @DeleteMapping("/api/v1/environments/{name}")
    void delete(
            @Parameter(name = "name", description = "Name of the environment to delete.")
            @PathVariable("name") String name
    );
}
