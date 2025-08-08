package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
