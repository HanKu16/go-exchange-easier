package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.user.AssignHomeUniversityRequest;
import com.go_exchange_easier.backend.dto.user.AssignHomeUniversityResponse;
import com.go_exchange_easier.backend.dto.user.UpdateDescriptionRequest;
import com.go_exchange_easier.backend.dto.user.UpdateUserDescriptionResponse;

public interface UserService {

    UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateDescriptionRequest request);
    AssignHomeUniversityResponse assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request);

}
