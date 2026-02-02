package com.go_exchange_easier.backend.infrastracture.exception;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.common.dto.error.FieldErrorDetail;
import com.go_exchange_easier.backend.common.dto.error.GlobalErrorDetail;
import com.go_exchange_easier.backend.common.exception.*;
import com.go_exchange_easier.backend.domain.auth.exception.*;
import com.go_exchange_easier.backend.infrastracture.security.jwt.MissingJwtClaimException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            GlobalExceptionHandler.class);

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ApiErrorResponse> handleFileUploadException(
            FileUploadException e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file.",
                List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ApiErrorResponse> handle(IllegalOperationException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.IllegalOperation.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Attempt to make illegal operation.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReferencedResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(ReferencedResourceNotFoundException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .ReferencedResourceNotFound.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                "The request could not be processed due to a referenced resource " +
                        "not found.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handle(ResourceAlreadyExistsException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(
                ApiErrorResponseCode.ResourceAlreadyExists.name(),
                e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.CONFLICT,
                "Resource already exists, so it can not be created again.",
                List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle(ResourceNotFoundException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .ResourceNotFound.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND,
                "The requested resource was not found.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

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
            DataCorruptionException.class,
            InvalidPrincipalTypeException.class,
            MissingDefaultRoleException.class,
            IllegalStateException.class,
            DataIntegrityViolationException.class,
            NullPointerException.class,
            RuntimeException.class
    })
    public ResponseEntity<ApiErrorResponse> handleApplicationException(Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected " +
                "server error occurred.", List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            NotOwnerOfResourceException.class,
            AccessDeniedException.class,
            AuthorizationDeniedException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAccessExceptions(Exception e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .PermissionDenied.name(), "");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.FORBIDDEN,
                "Access denied.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            DataAccessResourceFailureException.class,
            JDBCConnectionException.class,
            QueryTimeoutException.class,
            CannotCreateTransactionException.class
    })
    public ResponseEntity<ApiErrorResponse> handleDatabaseConnectionError(Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "Database server did not respond.",
                List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handle(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        List<FieldErrorDetail> fieldErrorDetails = getFieldErrorDetails(e);
        List<GlobalErrorDetail> globalErrorDetails = getGlobalErrorDetails(e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "Validation failed for one or more fields.",
                fieldErrorDetails, globalErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handle(HttpMessageNotReadableException e) {
        logger.error(e.getMessage(), e);
        List<GlobalErrorDetail> globalErrorDetails = List.of(new GlobalErrorDetail(
                ApiErrorResponseCode.InvalidRequestBody.name(),
                "Invalid request or body format."));
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "There was something wrong with " +
                "request body.", List.of(), globalErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handle(MissingRequestHeaderException e) {
        logger.error(e.getMessage(), e);
        List<GlobalErrorDetail> globalErrorDetails = List.of(new GlobalErrorDetail(
                ApiErrorResponseCode.MissingRequestHeader.name(), e.getMessage()));
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "There is missing header in your request.",
                List.of(), globalErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handle(MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail error = new GlobalErrorDetail(
                ApiErrorResponseCode.InvalidParameterType.name(),
                "Type mismatch error.");
        return new ResponseEntity<>(new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "Type mismatch error.",
                List.of(), List.of(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handle(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail error = new GlobalErrorDetail(
                ApiErrorResponseCode.MethodNotSupported.name(), e.getMessage());
        return new ResponseEntity<>(new ApiErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not supported.",
                List.of(), List.of(error)), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handle(AuthenticationException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .AuthenticationFailed.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({SignatureException.class, MissingJwtClaimException.class})
    public ResponseEntity<ApiErrorResponse> handleJwtException(Exception e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .InvalidToken.name(), "Failed to authenticate user.");
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED,
                "Authentication failed.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    private List<FieldErrorDetail> getFieldErrorDetails(
            MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorDetail(
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getDefaultMessage()
                ))
                .toList();
    }

    private List<GlobalErrorDetail> getGlobalErrorDetails(
            MethodArgumentNotValidException e) {
        return e.getBindingResult().getGlobalErrors().stream()
                .map(globalError -> new GlobalErrorDetail(
                        globalError.getCode(),
                        globalError.getDefaultMessage()
                ))
                .toList();
    }

}


