package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.core.domain.location.country.impl.CountryMapper;
import com.go_exchange_easier.backend.core.domain.university.impl.UniversityMapper;
import com.go_exchange_easier.backend.core.domain.user.User;
import com.go_exchange_easier.backend.core.domain.user.dto.UserDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.UserSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UniversityMapper universityMapper;
    private final CountryMapper countryMapper;

    public UserDetails toDetails(User user) {
        if (user == null) {
            return null;
        }
        return new UserDetails(
                user.getId(),
                user.getNick(),
                countryMapper.toDetails(user.getCountryOfOrigin()),
                universityMapper.toDetails(user.getHomeUniversity())
        );
    }

    public UserSummary toSummary(User user) {
        if (user == null) {
            return null;
        }
        return new UserSummary(
                user.getId(),
                user.getNick()
        );
    }

}
