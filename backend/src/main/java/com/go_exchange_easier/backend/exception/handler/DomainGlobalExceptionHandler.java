package com.go_exchange_easier.backend.exception.handler;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.dto.error.GlobalErrorDetail;
import com.go_exchange_easier.backend.exception.MailAlreadyExistsException;
import com.go_exchange_easier.backend.exception.UsernameAlreadyExistsException;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.base.ResourceAlreadyExistsException;
import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class DomainGlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            DomainGlobalExceptionHandler.class);

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.LoginAlreadyTaken.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Login is already taken.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityDoesNotExistException(
            ResourceNotFoundException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .ResourceNotFound.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND,
                "The requested resource was not found.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReferencedResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleReferencedResourceNotFoundException(
            ReferencedResourceNotFoundException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .ReferencedResourceNotFound.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                "The request could not be processed due to a referenced resource " +
                        "not found.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.ResourceAlreadyExists.name(),
                e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Resource already exists, so it can not be created again.",
                List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleMailAlreadyExistsException(
            MailAlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.MailAlreadyTaken.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Mail is already taken.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
