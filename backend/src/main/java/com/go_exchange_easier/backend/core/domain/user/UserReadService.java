package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.api.CoreUser;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.UserDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.UserProfile;
import com.go_exchange_easier.backend.core.domain.user.dto.UserWithAvatarSummary;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReadService {

    UserProfile getProfile(
            UUID userId,
            UUID currentUserId
    );

    Page<UserDetails> getPage(
            String nick,
            Pageable pageable
    );

    List<UserWithAvatarSummary> getFollowees(UUID userId);

    List<UniversityDetails> getFollowedUniversities(UUID userId);

    UserWithAvatarSummary getMe(UUID userId);

    CoreUser getUser(UUID userId);

    Map<UUID, CoreUser> getUsers(Set<UUID> userIds);

}
