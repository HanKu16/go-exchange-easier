package com.go_exchange_easier.backend.common.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import java.util.List;

@Schema(requiredProperties = {"status", "message", "fieldErrors", "globalErrors"})
public record ApiErrorResponse(

        HttpStatus status,
        String message,
        List<FieldErrorDetail> fieldErrors,
        List<GlobalErrorDetail> globalErrors

) { }
