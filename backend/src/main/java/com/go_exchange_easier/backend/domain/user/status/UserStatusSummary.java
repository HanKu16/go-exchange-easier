package com.go_exchange_easier.backend.domain.user.status;

import java.io.Serializable;

public record UserStatusSummary(

        Short id,
        String name

) implements Serializable { }
