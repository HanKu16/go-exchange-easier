package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.university.GetUniversityProfileResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import com.go_exchange_easier.backend.exception.domain.BadNumberOfSearchFiltersException;
import com.go_exchange_easier.backend.exception.domain.UniversityNotFoundException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public GetUniversityProfileResponse getProfile(
            int universityId, int currentUserId) {
        List<Object[]> rows = universityRepository.findProfileById(
                universityId, currentUserId);
        if (rows.isEmpty()) {
            throw new UniversityNotFoundException("University of id " +
                    universityId + " was not found.");
        }
        Object[] row = rows.getFirst();
        Short id = (Short) row[0];
        String originalName = (String) row[1];
        String englishName = (String) row[2];
        String linkToWebsite = (String) row[3];
        String cityName = (String) row[4];
        String countryName = (String) row[5];
        Boolean isFollowed = (Boolean) row[6];
        return new GetUniversityProfileResponse(id,
                originalName, englishName, linkToWebsite,
                cityName, countryName, isFollowed);
    }

    @Override
    public List<GetUniversityResponse> getByCountryId(Short countryId) {
        List<University> universities = universityRepository.findByCountryId(countryId);
        return mapToResponse(universities);
    }

    @Override
    public List<GetUniversityResponse> get(String englishName,
            String nativeName, Integer cityId, Short countryId) {
        int filtersCount = countFiltersApplied(englishName, nativeName, cityId, countryId);
        List<University> universities = new ArrayList<>();
        if (filtersCount != 1) {
            throw new BadNumberOfSearchFiltersException("Only 1 filter can be applied, but " +
                    filtersCount + " were applied.");
        }
        if (englishName != null) {
            universities = universityRepository.findByEnglishName(englishName);
        } else if (nativeName != null) {
            universities = universityRepository.findByOriginalName(nativeName);
        } else if (cityId != null) {
            universities = universityRepository.findByCityId(cityId);
        } else {
            universities = universityRepository.findByCountryId(countryId);
        }
        return mapToResponse(universities);
    }

    @Override
    public List<GetUniversityResponse> getByCityId(Integer cityId) {
        List<University> universities = universityRepository.findByCityId(cityId);
        return mapToResponse(universities);
    }

    private int countFiltersApplied(String englishName, String nativeName,
            Integer cityId, Short countryId) {
        int count = englishName != null ? 1 : 0;
        count += ((nativeName != null) ? 1 : 0);
        count += ((cityId != null) ? 1 : 0);
        count += ((countryId != null) ? 1 : 0);
        return count;
    }

    private List<GetUniversityResponse> mapToResponse(List<University> universities) {
        return universities.stream()
                .map(u -> new GetUniversityResponse(
                        u.getId(), u.getOriginalName(), u.getEnglishName(),
                        new GetUniversityResponse.CityDto(
                                u.getCity().getId(), u.getCity().getEnglishName(),
                                new GetUniversityResponse.CountryDto(
                                        u.getCity().getCountry().getId(),
                                        u.getCity().getCountry().getEnglishName()))))
                .toList();
    }

}
