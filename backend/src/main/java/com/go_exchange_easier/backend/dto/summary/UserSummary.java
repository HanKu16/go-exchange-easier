package com.go_exchange_easier.backend.dto.summary;

import com.go_exchange_easier.backend.model.User;

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
