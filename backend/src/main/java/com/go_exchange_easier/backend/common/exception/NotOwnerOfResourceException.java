package com.go_exchange_easier.backend.common.exception;

public class NotOwnerOfResourceException extends RuntimeException {

    public NotOwnerOfResourceException(String message) {
        super(message);
    }

}
