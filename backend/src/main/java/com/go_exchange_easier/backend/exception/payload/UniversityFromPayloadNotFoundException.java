package com.go_exchange_easier.backend.exception.payload;

import com.go_exchange_easier.backend.exception.base.InvalidPayloadException;

public class UniversityFromPayloadNotFoundException extends InvalidPayloadException {

    public UniversityFromPayloadNotFoundException(String message) {
        super(message);
    }

}
