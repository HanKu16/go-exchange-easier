package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.domain.user.dto.UserPublicProfile;

public interface UserPublicProfileProvider {

    UserPublicProfile getProfile(int userId);

}
