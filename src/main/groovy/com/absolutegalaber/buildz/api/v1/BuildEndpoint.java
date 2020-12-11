package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.domain.*;
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException;
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException;
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

import java.util.List;

@Tag(name = "builds", description = "Provides Management of (successful) Builds.")
public interface BuildEndpoint {
    @Operation(
            summary = "Search Builds.",
            description = "Search Builds fro project, branch, build-numbers and labels.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/BuildSearchResult")
                            )
                    )
            },
            tags = {"builds"})
    @PostMapping("/api/v1/builds/search")
    BuildSearchResult search(@RequestBody BuildSearch search);

    @Operation(
            summary = "Load a specific Build by ID.",
            description = "Load a specific build and returns a Build-info.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Build Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Build")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No such Build.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                            )
                    )
            },
            tags = {"builds"}
    )
    @GetMapping("/api/v1/builds/{buildId}")
    Build get(@PathVariable(name = "buildId") Long buildId) throws DataNotFoundException;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Encironment Created.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Build")
                    )
            )
    })
    @Operation(
            summary = "Create a new Build",
            description = "Creates a new Build.",
            tags = {"builds"})
    @PostMapping("/api/v1/builds/create")
    Build create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(ref = "#/components/schemas/Build", description = "The Build to create")
            @RequestBody Build build
    );

    @Operation(
            summary = "Add Labels",
            description = "Add labels to a Build.",
            tags = {"builds"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Label Added",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Build")
                    )
            )
    })
    @PostMapping("/api/v1/builds/add-labels/{buildId}")
    Build addLabels(
            @Parameter(name = "buildId", description = "Database ID of the build to add labels to.")
            @PathVariable(name = "buildId") Long buildId,
            @RequestBody List<BuildLabel> buildLabels
    ) throws InvalidRequestException;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Builds Loaded.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/EnvironmentBuilds")
                    )
            ), @ApiResponse(
            responseCode = "404",
            description = "Builds Loaded.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
            )
    )
    })
    @Operation(
            summary = "Builds of an Environment.",
            description = "All Build of an Environment.",
            tags = {"builds"}
    )
    @GetMapping("/api/v1/builds/of-environment/{name}")
    EnvironmentBuilds environment(
            @Parameter(name = "name", description = "Name of the Environment to search Builds for. ")
            @PathVariable(name = "name") String name
    ) throws DataNotFoundException;
}
