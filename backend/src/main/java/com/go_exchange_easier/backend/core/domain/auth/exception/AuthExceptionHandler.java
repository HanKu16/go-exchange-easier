package com.go_exchange_easier.backend.core.domain.auth.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class AuthExceptionHandler {

    private static final Logger logger = LogManager.getLogger(
            AuthExceptionHandler.class);


}
