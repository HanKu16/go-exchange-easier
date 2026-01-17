package com.go_exchange_easier.backend.exception.handler;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.dto.error.FieldErrorDetail;
import com.go_exchange_easier.backend.dto.error.GlobalErrorDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@ControllerAdvice
public class ValidationGlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            ValidationGlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        List<FieldErrorDetail> fieldErrorDetails = getFieldErrorDetails(e);
        List<GlobalErrorDetail> globalErrorDetails = getGlobalErrorDetails(e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "Validation failed for one or more fields.",
                fieldErrorDetails, globalErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        List<GlobalErrorDetail> globalErrorDetails = List.of(new GlobalErrorDetail(
                ApiErrorResponseCode.InvalidRequestBody.name(), e.getMessage()));
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "There was something wrong with " +
                "request body.", List.of(), globalErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(
            MissingRequestHeaderException e) {
        List<GlobalErrorDetail> globalErrorDetails = List.of(new GlobalErrorDetail(
                ApiErrorResponseCode.MissingRequestHeader.name(), e.getMessage()));
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, "There is missing header in your request.",
                List.of(), globalErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
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
