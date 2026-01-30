package com.go_exchange_easier.backend.domain.user.description;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserDescriptionRequest(

        @NotNull(message = "Description can not be null.")
        @Size(max = 500, message = "Description can not be longer than 500 characters.")
        @Schema(example = "Hi my name is Michael. I was on Erasmus exchange in Roma.")
        String description

) { }
