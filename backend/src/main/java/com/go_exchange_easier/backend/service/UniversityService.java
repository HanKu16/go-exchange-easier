package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.GetUniversityResponse;
import java.util.List;

public interface UniversityService {

    List<GetUniversityResponse> getByCountryId(Short countryId);

}
