package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.api.CoreUser;
import com.go_exchange_easier.backend.core.domain.location.country.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.core.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.*;
import com.go_exchange_easier.backend.core.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    UserProfileDetails getProfile(int userId, int currentUserId);
    Page<UserDetails> getPage(String nick, Pageable pageable);
    UserDescriptionDetails updateDescription(
            int userId, UpdateUserDescriptionRequest request);
    UniversitySummary assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request);
    UserStatusSummary updateStatus(int userId, UpdateUserStatusRequest request);
    CountrySummary assignCountryOfOrigin(
            int userId, AssignCountryOfOriginRequest request);
    List<UserWithAvatarSummary> getFollowees(int userId);
    List<UniversityDetails> getFollowedUniversities(int userId);
    UserWithAvatarSummary getMe(int userId);
    AvatarUrlSummary addAvatar(int userId, MultipartFile file);
    AvatarUrlSummary deleteAvatar(int userId);
    CoreUser getUser(int userId);
    Map<Integer, CoreUser> getUsers(Set<Integer> userIds);

}
