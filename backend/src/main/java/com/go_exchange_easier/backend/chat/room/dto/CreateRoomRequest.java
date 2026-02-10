package com.go_exchange_easier.backend.chat.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(

        @NotNull(message = "Target user id can not be null.")
        @Schema(example = "17")
        Integer targetUserId

) { }
