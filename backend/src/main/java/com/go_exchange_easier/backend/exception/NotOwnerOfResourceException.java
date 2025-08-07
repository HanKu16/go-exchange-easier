package com.go_exchange_easier.backend.exception;

public class NotOwnerOfResourceException extends RuntimeException {

    public NotOwnerOfResourceException(String message) {
        super(message);
    }

}
