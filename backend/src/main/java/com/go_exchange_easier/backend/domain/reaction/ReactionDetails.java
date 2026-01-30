package com.go_exchange_easier.backend.domain.reaction;

public record ReactionDetails(

        Short typeId,
        String name,
        Short count,
        Boolean isSet

) { }
