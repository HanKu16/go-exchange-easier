package com.go_exchange_easier.backend.domain.auth;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }

}
