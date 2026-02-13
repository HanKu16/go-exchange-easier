package com.go_exchange_easier.backend.chat.message;

import com.go_exchange_easier.backend.chat.message.dto.CreateMessageRequest;
import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequestMapping("/api/chat/rooms/{roomId}/messages")
@Tag(name = "Message")
public interface MessageApi {

    @GetMapping
    @Operation(summary = "Get page of messages from room")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Messages were successfully returned"),
            @ApiResponse(
                    responseCode = "403",
                    description = "Authenticated user was trying to access messages from " +
                            "room that he is not member of",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    ResponseEntity<SimplePage<MessageDetails>> get(
            @PathVariable UUID roomId,
            @ParameterObject Pageable pageable,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

    @PostMapping
    @Operation(summary = "Create message")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Message was successfully created",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "422",
                    description = "Room referenced in request body does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<MessageDetails> create(
            @PathVariable UUID roomId,
            @RequestBody @Valid CreateMessageRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

}
