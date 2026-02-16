package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.location.country.dto.CountryDetails;
import com.go_exchange_easier.backend.core.domain.location.country.CountryService;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.UserPublicProfileProvider;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarService;
import com.go_exchange_easier.backend.core.domain.user.dto.UserPublicProfile;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPublicProfileProviderImpl implements UserPublicProfileProvider {

    private final UserRepository userRepository;
    private final CountryService countryService;
    private final AvatarService avatarService;

    @Override
    @Cacheable(value="user-public-profiles", key="'user:' + #userId")
    public UserPublicProfile getProfile(int userId) {
        List<Object[]> rows = userRepository.findProfileById(userId);
        if (rows.isEmpty()) {
            throw new ResourceNotFoundException(
                    "User of id " + userId + " was not found");
        }
        Object[] row = rows.getFirst();
        Integer id = (Integer) row[0];
        String nick = (String) row[1];
        String description = (String) row[2];
        Short universityId = (Short) row[3];
        String universityOriginalName = (String) row[4];
        String universityEnglishName = (String) row[5];
        Short countryId = (Short) row[6];
        String countryName = (String) row[7];
        Short statusId = (Short) row[8];
        String statusName = (String) row[9];
        String avatarKey = row[10] != null ? (String) row[10] : null;
        String flagKey = row[11] != null ? (String) row[11] : null;
        String avatarUrl = null;
        if (avatarKey != null) {
            avatarUrl = avatarService.getUrl(avatarKey).original();
        }
        UniversitySummary university = universityId != null ?
                new UniversitySummary(universityId, universityOriginalName,
                        universityEnglishName) :
                null;
        CountryDetails country = countryId != null ?
                new CountryDetails(countryId, countryName, flagKey != null ?
                        countryService.getFlagUrl(flagKey) : null) :
                null;
        UserStatusSummary status = statusId != null ?
                new UserStatusSummary(statusId, statusName) :
                null;
        return new UserPublicProfile(id, nick, avatarUrl, description,
                university, country, status);
    }

}
