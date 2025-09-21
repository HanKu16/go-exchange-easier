package com.go_exchange_easier.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for assigning country to user as his country of origin")
public record AssignCountryOfOriginRequest(

        @NotNull(message = "Country id can not be null.")
        @Schema(example = "3")
        Short countryId

) { }
