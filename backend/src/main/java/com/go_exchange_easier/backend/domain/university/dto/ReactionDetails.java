package com.go_exchange_easier.backend.domain.university.dto;

public record ReactionDetails(

        Short typeId,
        String name,
        Short count,
        Boolean isSet

) { }
