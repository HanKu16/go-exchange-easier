package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.domain.location.country.dto.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.core.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.AssignCountryOfOriginRequest;
import com.go_exchange_easier.backend.core.domain.user.dto.AssignHomeUniversityRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface UserUpdateService {

    UserDescriptionDetails updateDescription(
            int userId,
            UpdateUserDescriptionRequest request
    );

    Optional<UniversitySummary> assignHomeUniversity(
            int userId,
            AssignHomeUniversityRequest request
    );

    Optional<UserStatusSummary> updateStatus(
            int userId,
            UpdateUserStatusRequest request
    );

    Optional<CountrySummary> assignCountryOfOrigin(
            int userId,
            AssignCountryOfOriginRequest request
    );

    AvatarUrlSummary addAvatar(
            int userId,
            MultipartFile file
    );

    AvatarUrlSummary deleteAvatar(int userId);

}
