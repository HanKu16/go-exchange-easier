package com.go_exchange_easier.backend.exception.handler;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.exception.InvalidPrincipalTypeException;
import com.go_exchange_easier.backend.exception.domain.MissingDefaultRoleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

}
