package com.go_exchange_easier.backend.core.api;

public record CoreUser(

        Integer id,
        String nick,
        String originalAvatarUrl,
        String thumbnailAvatarUrl

) {

    public static final CoreUser UNKNOWN = new CoreUser(
            null,
            "Unknown user",
            null,
            null
    );

}
