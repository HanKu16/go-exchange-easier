package com.go_exchange_easier.backend.common.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"field", "code", "message"})
public record FieldErrorDetail(

        String field,
        String code,
        String message

) { }
