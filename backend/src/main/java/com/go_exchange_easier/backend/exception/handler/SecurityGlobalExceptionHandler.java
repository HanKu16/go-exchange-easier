package com.go_exchange_easier.backend.exception.handler;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.dto.error.GlobalErrorDetail;
import com.go_exchange_easier.backend.exception.MissingJwtClaimException;
import com.go_exchange_easier.backend.exception.NotOwnerOfResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.security.SignatureException;
import java.util.List;

@ControllerAdvice
public class SecurityGlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            SecurityGlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(
            AuthenticationException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                        .AuthenticationFailed.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({SignatureException.class, MissingJwtClaimException.class})
    public ResponseEntity<ApiErrorResponse> handleSignatureException(
            SignatureException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .InvalidToken.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotOwnerOfResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleNotOwnerOfResourceException(
            NotOwnerOfResourceException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .DeletePermissionDenied.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.FORBIDDEN,
                "Access denied.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
