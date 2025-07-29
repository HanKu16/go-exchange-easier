package com.go_exchange_easier.backend.exception;

import com.go_exchange_easier.backend.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.security.SignatureException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(
            UsernameAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(), e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e) {
        List<String> validationErrors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                String.join(" ", validationErrors));
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed.");
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotExistingRoleException.class)
    public ResponseEntity<ErrorResponse> handleNotExistingRoleException(
            NotExistingRoleException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server error.");
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(
            SignatureException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Access denied.");
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingJwtClaimException.class)
    public ResponseEntity<ErrorResponse> handleMissingJwtClaimException(
            MissingJwtClaimException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.BAD_REQUEST);
    }

}
