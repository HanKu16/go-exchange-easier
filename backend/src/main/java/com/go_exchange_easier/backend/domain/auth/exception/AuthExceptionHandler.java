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

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenNotFoundException(
            TokenNotFoundException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenRevokedException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenRevokedException(
            TokenRevokedException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenExpiredException(
            TokenExpiredException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DeviceMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleDeviceMismatchException(
            DeviceMismatchException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
