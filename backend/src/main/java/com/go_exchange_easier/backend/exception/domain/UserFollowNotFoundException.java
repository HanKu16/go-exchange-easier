package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class UserFollowNotFoundException extends ResourceNotFoundException {

    public UserFollowNotFoundException(String message) {
        super(message);
    }

}
