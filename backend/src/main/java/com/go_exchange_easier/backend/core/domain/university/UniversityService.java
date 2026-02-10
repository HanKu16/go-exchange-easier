package com.go_exchange_easier.backend.core.domain.university;

import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityFilters;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityService {

    UniversityProfile getProfile(int universityId, int currentUserId);
    Page<UniversityDetails> getPage(UniversityFilters filters, Pageable pageable);

}
