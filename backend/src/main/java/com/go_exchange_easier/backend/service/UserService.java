package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.user.*;

public interface UserService {

    GetUserProfileResponse getProfile(int userId, int currentUserId);
    UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateUserDescriptionRequest request);
    AssignHomeUniversityResponse assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request);
    UpdateUserStatusResponse updateStatus(int userId,
            UpdateUserStatusRequest request);

}
