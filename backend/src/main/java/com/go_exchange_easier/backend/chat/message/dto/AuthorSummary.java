package com.go_exchange_easier.backend.chat.message.dto;

import java.io.Serializable;

public record AuthorSummary(

        String nick,
        String avatarUrl

) implements Serializable { }
