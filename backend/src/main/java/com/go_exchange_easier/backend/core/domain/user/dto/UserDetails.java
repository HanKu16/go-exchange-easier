package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.User;

public record UserDetails(

        Integer id,
        String nick,
        CountrySummary country,
        UniversitySummary university

) {

    public static UserDetails fromEntity(User u) {
        return new UserDetails(
                u.getId(),
                u.getNick(),
                u.getCountryOfOrigin() != null ?
                        CountrySummary.fromEntity(u.getCountryOfOrigin()) :
                        null,
                u.getHomeUniversity() != null ?
                        UniversitySummary.fromEntity(u.getHomeUniversity()) :
                        null
        );
    }

}
