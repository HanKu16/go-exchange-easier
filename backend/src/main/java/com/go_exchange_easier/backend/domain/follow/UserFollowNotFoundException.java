package com.go_exchange_easier.backend.domain.follow;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UserFollowNotFoundException extends ResourceNotFoundException {

    public UserFollowNotFoundException(String message) {
        super(message);
    }

}
