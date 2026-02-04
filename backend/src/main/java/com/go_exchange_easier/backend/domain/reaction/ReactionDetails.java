package com.go_exchange_easier.backend.domain.reaction;

public record ReactionDetails(

        ReactionType type,
        Short count,
        Boolean isSet

) { }
