package com.go_exchange_easier.backend.core.domain.auth.exception;

public class MailAlreadyExistsException extends RuntimeException {

    public MailAlreadyExistsException(String message) {
        super(message);
    }

}
