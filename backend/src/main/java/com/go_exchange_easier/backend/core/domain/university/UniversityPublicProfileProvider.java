package com.go_exchange_easier.backend.core.domain.university;

import com.go_exchange_easier.backend.core.domain.university.dto.UniversityPublicProfile;

public interface UniversityPublicProfileProvider {

    UniversityPublicProfile getProfile(int universityId);

}
