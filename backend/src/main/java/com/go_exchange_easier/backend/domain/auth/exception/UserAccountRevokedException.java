package com.go_exchange_easier.backend.domain.auth.exception;

public class UserAccountRevokedException extends RuntimeException {

    public UserAccountRevokedException(String message) {
        super(message);
    }

}
