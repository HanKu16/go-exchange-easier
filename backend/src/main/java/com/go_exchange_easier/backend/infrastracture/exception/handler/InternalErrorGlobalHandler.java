package com.go_exchange_easier.backend.infrastracture.exception.handler;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.domain.auth.InvalidPrincipalTypeException;
import com.go_exchange_easier.backend.common.exception.FileUploadException;
import com.go_exchange_easier.backend.domain.auth.MissingDefaultRoleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class InternalErrorGlobalHandler {

    private static final Logger logger = LogManager.getLogger(
            InternalErrorGlobalHandler.class);

    @ExceptionHandler({InvalidPrincipalTypeException.class,
            MissingDefaultRoleException.class,
            IllegalStateException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<ApiErrorResponse> handleInternalError(
            Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected " +
                "server error occurred.", List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataAccessResourceFailureException.class, JDBCConnectionException.class})
    public ResponseEntity<ApiErrorResponse> handleDatabaseConnectionError(
            Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "Database server did not respond.",
                List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ApiErrorResponse> handleFileUploadException(
            FileUploadException e) {
        logger.error(e.getMessage(), e);
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file.",
                List.of(), List.of());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
