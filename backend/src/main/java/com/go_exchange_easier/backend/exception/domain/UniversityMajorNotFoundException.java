package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class UniversityMajorNotFoundException extends ResourceNotFoundException {

    public UniversityMajorNotFoundException(String message) {
        super(message);
    }

}
