package com.go_exchange_easier.backend.exception.domain;

import com.go_exchange_easier.backend.exception.base.ResourceNotFoundException;

public class ExchangeNotFoundException extends ResourceNotFoundException {

    public ExchangeNotFoundException(String message) {
        super(message);
    }

}
