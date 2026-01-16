package com.go_exchange_easier.backend.dto.summary;

import java.io.Serializable;

public record UserStatusSummary(

        Short id,
        String name

) implements Serializable { }
