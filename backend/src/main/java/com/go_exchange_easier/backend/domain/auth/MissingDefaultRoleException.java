package com.go_exchange_easier.backend.domain.auth;

public class MissingDefaultRoleException extends RuntimeException {

    public MissingDefaultRoleException(String message) {
        super(message);
    }

}
