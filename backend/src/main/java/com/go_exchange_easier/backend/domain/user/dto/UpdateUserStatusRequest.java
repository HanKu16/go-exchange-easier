package com.go_exchange_easier.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for updating user status")
public record UpdateUserStatusRequest(

        @Schema(example = "3")
        Short statusId

) { }
