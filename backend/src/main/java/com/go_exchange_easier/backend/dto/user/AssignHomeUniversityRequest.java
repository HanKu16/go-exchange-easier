package com.go_exchange_easier.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for assigning home university to user")
public record AssignHomeUniversityRequest(

        @NotNull(message = "University id can not be null.")
        @Schema(example = "17")
        Short universityId

) { }
