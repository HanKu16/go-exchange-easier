package com.go_exchange_easier.backend.core.api;

public record CoreUser(

        Integer id,
        String nick,
        CoreAvatar avatar

) {

    public static final CoreUser UNKNOWN = new CoreUser(
            null,
            "Unknown user",
            null
    );

}
