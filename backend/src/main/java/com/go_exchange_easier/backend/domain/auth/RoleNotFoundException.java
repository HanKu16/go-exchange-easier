package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
