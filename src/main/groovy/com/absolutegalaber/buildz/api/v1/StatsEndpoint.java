package com.absolutegalaber.buildz.api.v1;

import com.absolutegalaber.buildz.domain.BuildStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "stats", description = "Provides Statistics.")
public interface StatsEndpoint {
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Data Loaded.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/BuildStats")
                    )
            )
    })
    @Operation(
            summary = "Get Stats.",
            description = "All Project, all branche, all buildLabel keys and some numbers.",
            tags = {"stats"}
    )
    @GetMapping("/api/v1/stats")
    BuildStats stats();
}
