package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.university.GetUniversityProfileResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import com.go_exchange_easier.backend.exception.domain.UniversityNotFoundException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return universities.stream()
                .map(u -> new GetUniversityResponse(
                        u.getId(), u.getOriginalName(), u.getEnglishName(),
                        new GetUniversityResponse.CityDto(
                                u.getCity().getId(), u.getCity().getEnglishName())))
                .toList();
    }

}
