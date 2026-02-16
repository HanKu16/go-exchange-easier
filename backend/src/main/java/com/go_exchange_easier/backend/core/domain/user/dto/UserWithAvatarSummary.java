package com.go_exchange_easier.backend.core.domain.user.dto;

import java.io.Serializable;

public record UserWithAvatarSummary(

        Integer id,
        String nick,
        String avatarUrl

) implements Serializable { }
