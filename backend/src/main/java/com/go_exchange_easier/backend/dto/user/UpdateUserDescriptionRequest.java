package com.go_exchange_easier.backend.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDescriptionRequest(

        @NotNull(message = "Description can not be null.")
        @Size(max = 500, message = "Description can not be longer than 500 characters.")
        String description

) { }
