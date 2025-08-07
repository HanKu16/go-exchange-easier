package com.go_exchange_easier.backend.exception;

public class ExchangeDoesNotExistException extends RuntimeException {

    public ExchangeDoesNotExistException(String message) {
        super(message);
    }

}
