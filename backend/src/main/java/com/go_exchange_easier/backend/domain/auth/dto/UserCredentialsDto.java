package com.go_exchange_easier.backend.domain.auth.dto;

public record UserCredentialsDto(

        Integer id,
        String username,
        String password,
        Boolean isEnabled)

{ }
