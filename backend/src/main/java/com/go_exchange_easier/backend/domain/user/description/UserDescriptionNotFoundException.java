package com.go_exchange_easier.backend.domain.user.description;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UserDescriptionNotFoundException extends ResourceNotFoundException {

    public UserDescriptionNotFoundException(String message) {
        super(message);
    }

}
