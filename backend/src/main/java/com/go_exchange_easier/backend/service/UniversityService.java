package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.details.UniversityDetails;
import com.go_exchange_easier.backend.dto.filter.UniversityFilters;
import com.go_exchange_easier.backend.dto.university.UniversityProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityService {

    UniversityProfile getProfile(int universityId, int currentUserId);
    Page<UniversityDetails> getPage(UniversityFilters filters, Pageable pageable);

}
