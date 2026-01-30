package com.go_exchange_easier.backend.domain.user.dto;

import com.go_exchange_easier.backend.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.domain.user.User;

public record UserDetails(

        Integer id,
        String nick,
        CountryDetails country,
        UniversitySummary university

) {

    public static UserDetails fromEntity(User u) {
        return new UserDetails(
                u.getId(),
                u.getNick(),
                u.getCountryOfOrigin() != null ?
                        CountryDetails.fromEntity(u.getCountryOfOrigin()) :
                        null,
                u.getHomeUniversity() != null ?
                        UniversitySummary.fromEntity(u.getHomeUniversity()) :
                        null
        );
    }

}
