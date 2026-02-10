package com.go_exchange_easier.backend.core.domain.auth.exception;

public class MissingDefaultRoleException extends RuntimeException {

    public MissingDefaultRoleException(String message) {
        super(message);
    }

}
