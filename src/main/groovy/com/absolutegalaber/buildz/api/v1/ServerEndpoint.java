package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.api.model.IServer;
import com.absolutegalaber.buildz.domain.Server;
import com.absolutegalaber.buildz.domain.exception.DataNotFoundException;
import com.absolutegalaber.buildz.domain.exception.InvalidRequestException;
import com.absolutegalaber.buildz.events.ReserveServerEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * An Endpoint that handles all API Requests related to the {@link Server}s
 * saved in the Buildz System.
 */
@Tag(name = "servers", description = "Provides Management of Servers.")
public interface ServerEndpoint {

    /**
     * An API path which allows end-users to send a GET Request with a single
     * parameter that is meant to be the name of a Server. If there is
     * a Server with the name provided it will be returned.
     * If there is no Server with the provided name, a
     * {@link DataNotFoundException}
     *
     * @param name the name of the server which should be
     *             returned
     * @return the Server server with the
     * provided name
     * @throws DataNotFoundException if there is no {@link Server} with the
     *                               provided {@code name}
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Server Loaded",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/IServer")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No Server with this name was found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ExceptionInfo")
                    )
            )
    })
    @Operation(
            summary = "Load an Server,",
            description = "Loads a specific Server by name,",
            tags = "server"
    )
    @GetMapping("/api/v1/servers/{name}")
    IServer get(
            @Parameter(name = "name", description = "Name of the configured Server.")
            @PathVariable("name") String name
    ) throws DataNotFoundException;

    /**
     * Fetch all of the names of the Servers that have been saved in the Buildz System.
     *
     * @return a list containing all Server
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Servers Loaded",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/IServer")
                    )
            )
    })
    @Operation(
            summary = "List all Servers",
            description = "Returns all known Servers.",
            tags = "servers"
    )
    @GetMapping("/api/v1/servers")
    List<IServer> list();

    /**
     * Reserve a Server for a specific person via a POST Request via the server's name.
     *
     * TODO: Discuss if a reservation should be override if this is called when there is an existing reservation
     *
     * @param name                      the name of a known Server
     * @param event                     all relevant information to reserving a server
     * @return                          the Reservation that was just created
     * @throws DataNotFoundException    if the provided server name is not associated to a known Server
     * @throws InvalidRequestException  if the provided reservation info is not valid
     */
    @PostMapping("/api/v1/servers/{name}/reservation")
    Server.Reservation reserveServer(
            @PathVariable("name") String name,
            @RequestBody ReserveServerEvent event
    ) throws DataNotFoundException, InvalidRequestException;

    /**
     * Remove the Reservation, if any, from the Server related to the provided Server name.
     *
     * @param name                      the name of a known Server
     * @throws DataNotFoundException    If the provided server name is not associated to a known Server
     */
    @DeleteMapping("/api/v1/servers/{name}/reservation")
    void releaseServer(@PathVariable("name") String name) throws DataNotFoundException;
}
