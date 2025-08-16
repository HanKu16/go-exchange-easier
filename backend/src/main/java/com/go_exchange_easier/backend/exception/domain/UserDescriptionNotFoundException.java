package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class UserDescriptionNotFoundException extends ResourceNotFoundException {

    public UserDescriptionNotFoundException(String message) {
        super(message);
    }

}
