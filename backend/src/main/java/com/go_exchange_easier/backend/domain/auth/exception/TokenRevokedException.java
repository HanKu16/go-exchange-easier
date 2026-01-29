package com.go_exchange_easier.backend.domain.auth.exception;

public class TokenRevokedException extends RuntimeException {

    public TokenRevokedException(String message) {
        super(message);
    }

}
