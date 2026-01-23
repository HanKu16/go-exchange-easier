package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.details.UniversityDetails;
import com.go_exchange_easier.backend.dto.details.UserDetails;
import com.go_exchange_easier.backend.dto.summary.UserSummary;
import com.go_exchange_easier.backend.dto.summary.UserWithAvatarSummary;
import com.go_exchange_easier.backend.dto.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface UserService {

    GetUserProfileResponse getProfile(int userId, int currentUserId);
    Page<UserDetails> getPage(String nick, Pageable pageable);
    UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateUserDescriptionRequest request);
    AssignHomeUniversityResponse assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request);
    UpdateUserStatusResponse updateStatus(int userId,
            UpdateUserStatusRequest request);
    AssignCountryOfOriginResponse assignCountryOfOrigin(
            int userId, AssignCountryOfOriginRequest request);
    List<UserSummary> getFollowees(int userId);
    List<UniversityDetails> getFollowedUniversities(int userId);
    UserWithAvatarSummary getMe(int userId);
    AvatarUrlSummary addAvatar(int userId, MultipartFile file);
    AvatarUrlSummary deleteAvatar(int userId);

}
