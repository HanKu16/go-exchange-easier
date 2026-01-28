package com.go_exchange_easier.backend.domain.user;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
