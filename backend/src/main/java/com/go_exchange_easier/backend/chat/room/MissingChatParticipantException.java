package com.go_exchange_easier.backend.chat.room;

public class MissingChatParticipantException extends RuntimeException {

    public MissingChatParticipantException(String message) {
        super(message);
    }
    
}
