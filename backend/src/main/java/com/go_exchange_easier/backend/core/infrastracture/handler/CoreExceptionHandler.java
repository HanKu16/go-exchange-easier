package com.go_exchange_easier.backend.core.infrastracture.handler;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.common.dto.error.GlobalErrorDetail;
import com.go_exchange_easier.backend.core.domain.auth.exception.*;
import com.go_exchange_easier.backend.core.infrastracture.security.jwt.MissingJwtClaimException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CoreExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            CoreExceptionHandler.class);

    @ExceptionHandler(DeviceMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handle(DeviceMismatchException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(),
                "Token came for device that he was not bound to.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handle(MailAlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.MailAlreadyTaken.name(),
                "This mail is already taken and can not be used.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Mail is already taken.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handle(TokenExpiredException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(),
                "Token expired.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(TokenNotFoundException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(),
                "Token was not found.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenRevokedException.class)
    public ResponseEntity<ApiErrorResponse> handle(TokenRevokedException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.AuthenticationFailed.name(),
                "Token is revoked.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handle(UsernameAlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.LoginAlreadyTaken.name(),
                "Login is already taken and can not be used again.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Login is already taken.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            InvalidPrincipalTypeException.class,
            MissingDefaultRoleException.class,
    })
    public ResponseEntity<ApiErrorResponse> handleApplicationException(Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected " +
                "server error occurred.", List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            MissingJwtClaimException.class,
            UserAccountRevokedException.class})
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(Exception e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .InvalidToken.name(), "Failed to authenticate user.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}


