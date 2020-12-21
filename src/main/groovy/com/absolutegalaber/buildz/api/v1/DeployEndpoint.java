package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.events.RegisterDeployEvent;
import com.absolutegalaber.buildz.domain.Deploy;
import com.absolutegalaber.buildz.domain.DeployLabel;
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException;
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException;
import com.absolutegalaber.buildz.domain.view.DeployView;
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

/**
 * An Endpoint that handles all API Requests related to the {@link Deploy}s
 * saved in the Buildz System.
 */
@Tag(name = "deploys", description = "Provides Management of (successful) Deploys.")
public interface DeployEndpoint {

    // TODO: Add support for searching via deploy date/time and/or label?

    /**
     * An API path which, via a GET Request, attempts to find all Deploy related to a Server via that Server's name.
     *
     * @param serverName                The name of the Server whose Deploys should be listed
     * @return                          All Deploys that are associated to a specific Server
     * @throws DataNotFoundException    if serverName is not associated to any Server
     */
    @Operation(
            summary = "Load a list of Deploys by Server Name.",
            description = "Load a list of Deploys and returns all Deploy-info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Server found and Deploys returned",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Deploy")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No such Server.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                            )
                    )
            },
            tags = "deploys"
    )
    @GetMapping("/api/v1/deploys/on/{serverName}")
    List<DeployView> list(@PathVariable(name = "serverName") String serverName) throws DataNotFoundException;

    /**
     * An API path which, via a GET Request, attempts to find a {@link Deploy}
     * via its ID. This ID needs to be provided as a parameter in the GET
     * Request.
     *
     * @param deployId                  the ID of the {@link Deploy} which is
     *                                  being requested
     * @return                          The {@link Deploy} with the provided ID
     * @throws DataNotFoundException    If the provided ID is not associated to
     *                                  any {@link Deploy} in the Buildz System
     */
    @Operation(
            summary = "Load a specific Deploy by ID.",
            description = "Load a specific deploy and returns a Deploy-info.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deploy Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/Deploy")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No such Deploy.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                            )
                    )
            },
            tags = {"deploys"}
    )
    @GetMapping("/api/v1/deploys/{deployId}")
    DeployView get(@PathVariable(name = "deployId") Long deployId) throws DataNotFoundException;

    /**
     * An API path, which via a POST Request, attempts to register a
     * {@link Deploy} that happened on a specific
     * {@link com.absolutegalaber.buildz.domain.Server}.
     *
     * @param event                     an API event that should contain all
     *                                  necessary data to register a
     *                                  {@link Deploy} on a specific
     *                                  {@link com.absolutegalaber.buildz.domain.Server}
     * @return                          All information that the Buildz System
     *                                  knows about the deploy
     * @throws InvalidRequestException  if the provided project, branch,
     *                                  and buildNumber are not associated
     *                                  to a known Build
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deploy Registered.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Deploy")
                    )
            )
    })
    @Operation(
            summary = "Register a new Deploy",
            description = "Registers a new Deploy of a Build to a Server.",
            tags = {"deploys"})
    @PostMapping("/api/v1/deploy/create")
    DeployView register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    ref = "#/components/schemas/Deploy",
                    description = "A deployment registration Event"
            )
            @RequestBody RegisterDeployEvent event
    ) throws InvalidRequestException;

    /**
     * An API path which, via a POST Request, which attempts to add
     * {@link com.absolutegalaber.buildz.domain.DeployLabel}s to a specific
     * {@link Deploy}.
     *
     * @param deployId                  the ID of a specific {@link Deploy} to
     *                                  which the {@link DeployLabel}s
     *                                  generated by this Request should be
     *                                  added
     * @param deployLabels              a map which be used to generate and
     *                                  save one or more {@link DeployLabel}s
     *                                  to a specific {@link Deploy}
     * @return                          The updated {@link Deploy}
     * @throws InvalidRequestException  if the provided {@code deployId} is
     *                                  not associated to a {@link Deploy} in
     *                                  Buildz System
     */
    @Operation(
            summary = "Add Labels",
            description = "Add labels to a Deploy.",
            tags = {"deploys"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Label Added",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/Deploy")
                    )
            )
    })
    @PostMapping("/api/v1/deploys/add-labels/{deployId}")
    DeployView addLabels(
            @Parameter(name = "deployId", description = "Database ID of the deploy to add labels to.")
            @PathVariable(name = "deployId") Long deployId,
            @RequestBody List<DeployLabel> deployLabels
    ) throws InvalidRequestException;
}
