package com.go_exchange_easier.backend.core.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateNickRequest(

        @NotBlank(message = "Nick can not be blank.")
        @Size(min = 3, max = 20, message = "Nick must have between 3 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Nick can only contain letters and numbers.")
        String nick

) { }
