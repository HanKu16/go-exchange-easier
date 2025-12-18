package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.university.GetUniversityProfileResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import com.go_exchange_easier.backend.exception.domain.UniversityNotFoundException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.specification.UniversitySpecification;
import com.go_exchange_easier.backend.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<GetUniversityResponse> getPage(String englishName, String nativeName,
            Integer cityId, Short countryId, Pageable pageable) {
        Specification<University> specification = (root, query, cb) -> null;
        if (nativeName != null) {
            specification = specification.and(UniversitySpecification
                    .hasOriginalName(nativeName));
        }
        if (englishName != null) {
            specification = specification.and(UniversitySpecification
                    .hasEnglishName(englishName));
        }
        if (cityId != null) {
            specification = specification.and(UniversitySpecification
                    .hasCityId(cityId));
        }
        if (countryId != null) {
            specification = specification.and(UniversitySpecification
                    .hasCountryId(countryId));
        }
        Page<University> universities = universityRepository
                .findAll(specification, pageable);
        return universities.map(this::mapToDto);
    }

    private GetUniversityResponse mapToDto(University university) {
        return new GetUniversityResponse(
                university.getId(), university.getOriginalName(),
                university.getEnglishName(),
                new GetUniversityResponse.CityDto(university.getCity().getId(),
                        university.getCity().getEnglishName(),
                        new GetUniversityResponse.CountryDto(
                                university.getCity().getCountry().getId(),
                                university.getCity().getCountry().getEnglishName())));
    }

}
