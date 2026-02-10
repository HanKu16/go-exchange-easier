package com.go_exchange_easier.backend.core.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AssignCountryOfOriginRequest(

        @Schema(example = "3")
        Short countryId

) { }
