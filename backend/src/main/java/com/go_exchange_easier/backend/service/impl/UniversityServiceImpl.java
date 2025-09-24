package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
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
