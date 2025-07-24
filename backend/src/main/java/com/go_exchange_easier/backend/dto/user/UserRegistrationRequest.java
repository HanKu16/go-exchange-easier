package com.go_exchange_easier.backend.dto.user;

import jakarta.validation.constraints.*;

public record UserRegistrationRequest(

        @NotNull(message = "Login is needed to register new user.")
        @Size(min = 6, max = 20, message = "Login must have between 6 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Login can only contain letters and numbers.")
        String login,

        @NotNull(message = "Password is needed to register new user.")
        @Size(min = 8, max = 20, message = "Password must have between 8 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Password can only contain letters and numbers.")
        String password,

        @Email(message = "This is not valid mail.")
        @Size(max = 254, message = "Mail can not be longer than 254 characters.")
        String mail,

        @NotBlank(message = "Nick can not be blank.")
        @Size(min = 3, max = 20, message = "Nick must have between 3 and 20 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message =
                "Nick can only contain letters and numbers.")
        String nick

) { }
