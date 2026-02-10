package com.go_exchange_easier.backend.core.infrastracture.security.jwt;

public class MissingJwtClaimException extends RuntimeException {

    public MissingJwtClaimException(String message) {
        super(message);
    }

}
