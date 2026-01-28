package com.go_exchange_easier.backend.infrastracture.security.jwt;

public class MissingJwtClaimException extends RuntimeException {

    public MissingJwtClaimException(String message) {
        super(message);
    }

}
