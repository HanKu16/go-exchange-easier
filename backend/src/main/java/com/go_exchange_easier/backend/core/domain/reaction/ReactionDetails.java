package com.go_exchange_easier.backend.core.domain.reaction;

import java.io.Serializable;

public record ReactionDetails(

        ReactionType type,
        Short count,
        Boolean isSet

) implements Serializable { }
