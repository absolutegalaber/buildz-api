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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "environments", description = "Provides Management of Environments.")
public interface EnvironmentEndpoint {

    @ApiResponses({
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
                    description = "Environment not valid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                    )
            )
    })
    @Operation(
            summary = "Create / Update an Environment,",
            description = "Create / Update an Environment,",
            tags = "environments"
    )
    @PostMapping(value = "/api/v1/environments")
    Environment save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Environment")
                    ),
                    description = "The Environment to update/create.",
                    required = true
            )
            @RequestBody Environment environment
    ) throws InvalidRequestException;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Environment Loaded",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Environment")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No Environment with this name was found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                    )
            )
    })
    @Operation(
            summary = "Load an Environment,",
            description = "Loads a specific Environment by name,",
            tags = "environments"
    )
    @GetMapping("/api/v1/environments/{name}")
    Environment get(
            @Parameter(name = "name", description = "Name of the configured Environment.")
            @PathVariable("name") String name
    ) throws DataNotFoundException;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Verification results returned.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/EnvironmentBuilds")
                    )
            )
    })
    @Operation(
            summary = "Verify Artefacts,",
            description = "Searches for Builds according to the set of Artifacts provided.",
            tags = "environments"
    )
    @PostMapping("/api/v1/environments/verify-artifacts")
    EnvironmentBuilds verifyEnvironment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Artifact"),
                            array = @ArraySchema(minItems = 0)
                    )
            )
            @RequestBody Set<Artifact> artifacts
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Environment deleted if it was found."
            )
    })
    @Operation(
            summary = "Deletes an Environment",
            description = "Deletes an Environment",
            tags = "environments"
    )
    @DeleteMapping("/api/v1/environments/{name}")
    void delete(
            @Parameter(name = "name", description = "Name of the environment to delete.")
            @PathVariable("name") String name
    );
}