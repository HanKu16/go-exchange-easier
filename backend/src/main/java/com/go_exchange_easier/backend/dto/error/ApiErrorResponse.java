package com.go_exchange_easier.backend.dto.error;

import org.springframework.http.HttpStatus;
import java.util.List;

public record ApiErrorResponse(

        HttpStatus status,
        String message,
        List<ErrorDetail> errors

) { }
