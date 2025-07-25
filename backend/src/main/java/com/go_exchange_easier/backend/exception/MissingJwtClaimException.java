package com.go_exchange_easier.backend.exception;

public class MissingJwtClaimException extends RuntimeException {

    public MissingJwtClaimException(String message) {
        super(message);
    }

}
