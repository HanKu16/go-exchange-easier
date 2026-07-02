package com.go_exchange_easier.backend.core.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for assigning home university to user")
public record AssignHomeUniversityRequest(

        @Schema(example = "17")
        Short universityId

) { }
