package com.go_exchange_easier.backend.dto.error;

public record FieldErrorDetail(

        String field,
        String code,
        String message

) { }
