package com.go_exchange_easier.backend.chat.infrastracture;

import com.go_exchange_easier.backend.chat.domain.exception.MissingRoomParticipantException;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponseCode;
import com.go_exchange_easier.backend.common.dto.error.GlobalErrorDetail;
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
public class ChatExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            ChatExceptionHandler.class);

    @ExceptionHandler(MissingRoomParticipantException.class)
    public ResponseEntity<ApiErrorResponse> handle(MissingRoomParticipantException e) {
        logger.error(e.getMessage(), e);
        GlobalErrorDetail globalError = new GlobalErrorDetail(ApiErrorResponseCode
                .ResourceNotFound.name(), e.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND,
                "The requested resource was not found.", List.of(), List.of(globalError));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
