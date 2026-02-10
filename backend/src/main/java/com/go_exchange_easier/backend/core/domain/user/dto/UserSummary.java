package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.user.User;

public record UserSummary(

        Integer id,
        String nick

) {

    public static UserSummary fromEntity(User u) {
        return new UserSummary(
                u.getId(),
                u.getNick()
        );
    }

}
