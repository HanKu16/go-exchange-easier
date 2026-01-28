package com.go_exchange_easier.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for a successful update user status")
public record UpdateUserStatusResponse(

        Integer userId,
        Short statusId,
        String statusName

) { }
