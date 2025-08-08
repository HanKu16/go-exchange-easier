package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class UserStatusNotFoundException extends ResourceNotFoundException {

    public UserStatusNotFoundException(String message) {
        super(message);
    }

}
