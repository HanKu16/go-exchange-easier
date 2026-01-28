package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UniversityReviewReactionNotFoundException extends
        ResourceNotFoundException {

    public UniversityReviewReactionNotFoundException(
            String message) {
        super(message);
    }

}
