package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/chat/rooms")
@Tag(name = "Room")
public interface RoomApi {

    @GetMapping
    @Operation(summary = "Get page of user rooms")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rooms were successfully returned"),
    })
    ResponseEntity<SimplePage<RoomSummary>> getUserRoomsPage(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size);

}
