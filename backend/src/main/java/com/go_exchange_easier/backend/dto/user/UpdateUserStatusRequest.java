package com.go_exchange_easier.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for updating user status")
public record UpdateUserStatusRequest(

        @NotNull(message = "Status id can not be null.")
        @Schema(example = "3")
        Short statusId

) { }
