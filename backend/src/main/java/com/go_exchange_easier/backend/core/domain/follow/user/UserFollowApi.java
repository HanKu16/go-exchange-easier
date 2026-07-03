package com.go_exchange_easier.backend.core.domain.follow.user;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users/{followeeId}/follow")
@Tag(name = "User follow")
public interface UserFollowApi {

    @PostMapping
    @Operation(summary = "Create user follow")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Follow was successfully created"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Followee user was not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Follow already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Void> create(
            @PathVariable UUID followeeId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @DeleteMapping
    @Operation(summary = "Delete user follow")
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
            @PathVariable UUID followeeId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}
