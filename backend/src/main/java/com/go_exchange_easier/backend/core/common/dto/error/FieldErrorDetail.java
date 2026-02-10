package com.go_exchange_easier.backend.core.common.dto.error;

public record FieldErrorDetail(

        String field,
        String code,
        String message

) { }
