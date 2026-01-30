package com.go_exchange_easier.backend.domain.user;

import com.go_exchange_easier.backend.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.domain.user.dto.*;
import com.go_exchange_easier.backend.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.domain.user.status.UserStatusSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface UserService {

    UserProfileDetails getProfile(int userId, int currentUserId);
    Page<UserDetails> getPage(String nick, Pageable pageable);
    UserDescriptionDetails updateDescription(
            int userId, UpdateUserDescriptionRequest request);
    UniversitySummary assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request);
    UserStatusSummary updateStatus(int userId, UpdateUserStatusRequest request);
    CountryDetails assignCountryOfOrigin(
            int userId, AssignCountryOfOriginRequest request);
    List<UserWithAvatarSummary> getFollowees(int userId);
    List<UniversityDetails> getFollowedUniversities(int userId);
    UserWithAvatarSummary getMe(int userId);
    AvatarUrlSummary addAvatar(int userId, MultipartFile file);
    AvatarUrlSummary deleteAvatar(int userId);

}
