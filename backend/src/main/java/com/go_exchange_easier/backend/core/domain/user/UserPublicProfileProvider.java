package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.domain.user.dto.UserPublicProfile;
import java.util.UUID;

public interface UserPublicProfileProvider {

    UserPublicProfile getProfile(UUID userId);

}
