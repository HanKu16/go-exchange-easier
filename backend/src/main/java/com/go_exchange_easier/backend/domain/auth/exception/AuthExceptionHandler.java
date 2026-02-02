package com.go_exchange_easier.backend.domain.auth.exception;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.common.dto.error.GlobalErrorDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class AuthExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            AuthExceptionHandler.class);


}
