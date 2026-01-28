package com.go_exchange_easier.backend.domain.follow;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UniversityFollowNotFoundException extends ResourceNotFoundException {

    public UniversityFollowNotFoundException(String message) {
        super(message);
    }

}
