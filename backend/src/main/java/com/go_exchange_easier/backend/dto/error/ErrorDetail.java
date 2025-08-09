package com.go_exchange_easier.backend.dto.error;

public record ErrorDetail(

        String field,
        String code,
        String message

) { }
