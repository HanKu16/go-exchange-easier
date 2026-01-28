package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UniversityReviewNotFoundException extends ResourceNotFoundException {

    public UniversityReviewNotFoundException(String message) {
        super(message);
    }

}
