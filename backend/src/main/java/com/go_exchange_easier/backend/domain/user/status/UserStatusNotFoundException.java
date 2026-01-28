package com.go_exchange_easier.backend.domain.user.status;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UserStatusNotFoundException extends ResourceNotFoundException {

    public UserStatusNotFoundException(String message) {
        super(message);
    }

}
