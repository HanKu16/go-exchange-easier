package com.go_exchange_easier.backend.domain.exchange;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;

public class ExchangeNotFoundException extends ResourceNotFoundException {

    public ExchangeNotFoundException(String message) {
        super(message);
    }

}
