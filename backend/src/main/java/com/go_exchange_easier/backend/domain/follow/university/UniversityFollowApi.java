package com.go_exchange_easier.backend.domain.follow.university;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.domain.auth.dto.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/universities/{universityId}/follow")
@Tag(name = "University follow")
public interface UniversityFollowApi {

    @PostMapping
    @Operation(summary = "Create university follow")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Follow was successfully created"),
            @ApiResponse(
                    responseCode = "404",
                    description = "University that supposed to be followed was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Follow already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Void> create(
            @PathVariable Short universityId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @DeleteMapping
    @Operation(summary = "Delete university follow")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Follow was successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Follow was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    ResponseEntity<Void> delete(
            @PathVariable Short universityId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}
