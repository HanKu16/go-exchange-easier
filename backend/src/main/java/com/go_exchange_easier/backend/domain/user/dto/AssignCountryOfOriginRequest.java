package com.go_exchange_easier.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for assigning country to user as his country of origin")
public record AssignCountryOfOriginRequest(

        @Schema(example = "3")
        Short countryId

) { }
