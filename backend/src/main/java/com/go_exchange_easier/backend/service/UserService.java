package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.user.*;

public interface UserService {

    UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateDescriptionRequest request);
    AssignHomeUniversityResponse assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request);
    UpdateUserStatusResponse updateStatus(int userId,
            UpdateUserStatusRequest request);

}
