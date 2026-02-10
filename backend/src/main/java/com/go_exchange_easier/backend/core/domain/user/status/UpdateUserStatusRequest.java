package com.go_exchange_easier.backend.core.domain.user.status;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserStatusRequest(

        @Schema(example = "3")
        Short statusId

) { }
