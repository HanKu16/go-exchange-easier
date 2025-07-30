package com.go_exchange_easier.backend.dto;

public record ErrorResponse(

        Integer status,
        String message

) { }
