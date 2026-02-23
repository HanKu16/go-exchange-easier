package com.go_exchange_easier.backend.common.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"code", "message"})
public record GlobalErrorDetail(

        String code,
        String message

) { }
