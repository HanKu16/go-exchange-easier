package com.go_exchange_easier.backend.domain.fieldofstudy;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UniversityMajorNotFoundException extends ResourceNotFoundException {

    public UniversityMajorNotFoundException(String message) {
        super(message);
    }

}
