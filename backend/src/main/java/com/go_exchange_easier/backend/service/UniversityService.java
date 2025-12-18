package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.GetUniversityProfileResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityService {

    GetUniversityProfileResponse getProfile(int universityId, int currentUserId);
    Page<GetUniversityResponse> getPage(String englishName, String nativeName,
            Integer cityId, Short countryId, Pageable pageable);

}
