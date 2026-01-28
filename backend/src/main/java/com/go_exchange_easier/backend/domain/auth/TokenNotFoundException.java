package com.go_exchange_easier.backend.domain.auth;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
