package com.go_exchange_easier.backend.chat.domain.room;

import com.go_exchange_easier.backend.chat.domain.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.domain.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.domain.room.dto.RoomPreview;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequestMapping("/api/chat/rooms")
@Tag(name = "Room")
public interface RoomApi {

    @GetMapping
    @Operation(summary = "Get page of user rooms previews")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rooms were successfully returned"),
    })
    ResponseEntity<SimplePage<RoomPreview>> getUserRoomsPage(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size);

    @PostMapping
    @Operation(summary = "Get or create room if room does not exist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Room was successfully returned"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<RoomSummary> getOrCreate(
            @RequestBody @Valid CreateRoomRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticationUser);

    @GetMapping("/{roomId}")
    @Operation(summary = "Get room")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rooms were successfully returned"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room was not found or user is not member of the room",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<RoomSummary> getById(
            @PathVariable UUID roomId,
            @AuthenticationPrincipal AuthenticatedUser authenticationUser);

    @PatchMapping("/{roomId}/read-status")
    @Operation(summary = "Update read status for particular room")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Read status was successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room was not found or user is not member of the room",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<Void> updateReadStatus(
            @PathVariable UUID roomId,
            @AuthenticationPrincipal AuthenticatedUser authenticationUser);

}
