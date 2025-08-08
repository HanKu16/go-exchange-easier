package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class UniversityNotFoundException extends ResourceNotFoundException {

    public UniversityNotFoundException(String message) {
        super(message);
    }

}
