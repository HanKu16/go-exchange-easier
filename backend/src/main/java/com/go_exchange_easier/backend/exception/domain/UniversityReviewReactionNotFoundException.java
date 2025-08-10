package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class UniversityReviewReactionNotFoundException extends
        ResourceNotFoundException {

    public UniversityReviewReactionNotFoundException(
            String message) {
        super(message);
    }

}
