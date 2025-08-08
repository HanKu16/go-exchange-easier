package com.go_exchange_easier.backend.exception;

import com.go_exchange_easier.backend.dto.ErrorResponse;
import com.go_exchange_easier.backend.exception.base.InvalidPayloadException;
import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.*;
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

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotExistingRoleException(
            RoleNotFoundException e) {
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

    @ExceptionHandler(InvalidPrincipalTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPrincipalTypeException(
            InvalidPrincipalTypeException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityDoesNotExistException(
            ResourceNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Resource was not found.");
        logger.error(e.getMessage(), e);
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidPayloadException(
            InvalidPayloadException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage());
        logger.error(e.getMessage(), e);
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotOwnerOfResourceException.class)
    public ResponseEntity<ErrorResponse> handleNotOwnerOfResourceException(
            NotOwnerOfResourceException e) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "You are not entitled to do this.");
        return new ResponseEntity<ErrorResponse>(
                response, HttpStatus.FORBIDDEN);
    }

}
