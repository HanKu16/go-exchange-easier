package com.go_exchange_easier.backend.chat.domain.exception;

public class MissingRoomParticipantException extends RuntimeException {

    public MissingRoomParticipantException(String message) {
        super(message);
    }
    
}
