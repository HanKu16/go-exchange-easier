package com.go_exchange_easier.backend.core.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record RegistrationRequest(

        @NotNull(message = "Login is needed to register new user.")
        @Size(min = 6, max = 20, message = "Login must have between 6 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Login can only contain letters and numbers.")
        @Schema(example = "DuckyKentucky1")
        String login,

        @NotNull(message = "Password is needed to register new user.")
        @Size(min = 8, max = 20, message = "Password must have between 8 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Password can only contain letters and numbers.")
        @Schema(example = "Basketball23")
        String password,

        @Email(message = "This is not valid mail.")
        @Size(max = 254, message = "Mail can not be longer than 254 characters.")
        @Schema(example = "jordan23@gmail.com")
        String mail,

        @NotBlank(message = "Nick can not be blank.")
        @Size(min = 3, max = 20, message = "Nick must have between 3 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Nick can only contain letters and numbers.")
        @Schema(example = "Michael")
        String nick

) { }
