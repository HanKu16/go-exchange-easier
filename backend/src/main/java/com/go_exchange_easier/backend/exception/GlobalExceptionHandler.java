package com.go_exchange_easier.backend.exception;

import com.go_exchange_easier.backend.dto.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(
            GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(
            UsernameAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(), e.getMessage());
        logger.error(e.getMessage());
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
        logger.error(response.message());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed.");
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RoleDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleNotExistingRoleException(
            RoleDoesNotExistException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server error.");
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(
            SignatureException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Access denied.");
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingJwtClaimException.class)
    public ResponseEntity<ErrorResponse> handleMissingJwtClaimException(
            MissingJwtClaimException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniversityDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUniversityDoesNotExistException(
            UniversityDoesNotExistException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserDoesNotExistException(
            UserDoesNotExistException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserStatusDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserStatusDoesNotExistException(
            UserStatusDoesNotExistException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage());
        logger.error(e.getMessage());
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.NOT_FOUND);
    }

}
