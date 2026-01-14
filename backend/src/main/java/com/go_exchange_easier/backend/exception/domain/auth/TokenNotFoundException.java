package com.go_exchange_easier.backend.exception.domain.auth;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
