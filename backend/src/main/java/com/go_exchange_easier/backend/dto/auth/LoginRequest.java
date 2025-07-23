package com.go_exchange_easier.backend.dto.auth;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotNull(message = "Login can not be blank.")
        String login,

        @NotNull(message = "Password can not be blank.")
        String password

) {

}
