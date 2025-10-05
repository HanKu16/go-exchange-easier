package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.GetUniversityProfileResponse;
import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import java.util.List;

public interface UniversityService {

    GetUniversityProfileResponse getProfile(int universityId, int currentUserId);
    List<GetUniversityResponse> getByCountryId(Short countryId);

}
