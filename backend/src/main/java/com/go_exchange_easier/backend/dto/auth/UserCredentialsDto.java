package com.go_exchange_easier.backend.dto.auth;

public record UserCredentialsDto(

        Integer id,
        String username,
        String password,
        Boolean isEnabled)

{ }
