package com.go_exchange_easier.backend.dto.details;

public record ReactionDetails(

        Short typeId,
        String name,
        Short count,
        Boolean isSet

) { }
