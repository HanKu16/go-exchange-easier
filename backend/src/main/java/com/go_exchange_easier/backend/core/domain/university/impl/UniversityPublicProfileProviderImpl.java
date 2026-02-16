package com.go_exchange_easier.backend.core.domain.university.impl;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.location.city.CityDetails;
import com.go_exchange_easier.backend.core.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.core.domain.location.country.CountryService;
import com.go_exchange_easier.backend.core.domain.university.UniversityPublicProfileProvider;
import com.go_exchange_easier.backend.core.domain.university.UniversityRepository;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityPublicProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityPublicProfileProviderImpl implements
        UniversityPublicProfileProvider {

    private final UniversityRepository universityRepository;
    private final CountryService countryService;

    @Override
    @Cacheable(value="university-public-profiles",
            key="'university:' + #universityId")
    public UniversityPublicProfile getProfile(int universityId) {
        List<Object[]> rows = universityRepository.findProfileById(
                universityId);
        if (rows.isEmpty()) {
            throw new ResourceNotFoundException("University of id " +
                    universityId + " was not found.");
        }
        Object[] row = rows.getFirst();
        Short id = (Short) row[0];
        String originalName = (String) row[1];
        String englishName = (String) row[2];
        String linkToWebsite = (String) row[3];
        String cityName = (String) row[4];
        String countryName = (String) row[5];
        Integer cityId = (Integer) row[6];
        Short countryId = (Short) row[7];
        String countryFlagKey = row[8] != null ? (String) row[8] : null;
        return new UniversityPublicProfile(id,
                originalName, englishName, linkToWebsite,
                new CityDetails(cityId, cityName, new CountryDetails(
                        countryId, countryName,
                        countryFlagKey != null ?
                                countryService.getFlagUrl(countryFlagKey) :
                                null)));
    }

}
