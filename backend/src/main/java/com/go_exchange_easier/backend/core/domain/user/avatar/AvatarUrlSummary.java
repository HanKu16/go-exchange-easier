package com.go_exchange_easier.backend.core.domain.user.avatar;

import java.io.Serializable;

public record AvatarUrlSummary(

        String original,
        String thumbnail

) implements Serializable { }
