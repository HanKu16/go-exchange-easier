package com.go_exchange_easier.backend.chat.domain.room.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public record CreateRoomRequest(

        @NotNull(message = "Target user id can not be null.")
        UUID targetUserId

) implements Serializable { }
