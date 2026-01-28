package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class UniversityNotFoundException extends ResourceNotFoundException {

    public UniversityNotFoundException(String message) {
        super(message);
    }

}
