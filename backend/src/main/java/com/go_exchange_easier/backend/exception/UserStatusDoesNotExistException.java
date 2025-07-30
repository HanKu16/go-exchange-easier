package com.go_exchange_easier.backend.exception;

public class UserStatusDoesNotExistException extends RuntimeException {

    public UserStatusDoesNotExistException(String message) {
        super(message);
    }

}
