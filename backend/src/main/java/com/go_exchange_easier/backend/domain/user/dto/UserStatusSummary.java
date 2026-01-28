package com.go_exchange_easier.backend.domain.user.dto;

import java.io.Serializable;

public record UserStatusSummary(

        Short id,
        String name

) implements Serializable { }
