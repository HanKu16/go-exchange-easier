package com.go_exchange_easier.backend.core.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface CoreFacade {

    CoreUser getUserById(UUID userId);

    Map<UUID, CoreUser> getUsersByIds(Set<UUID> userIds);

    CoreAvatar getAvatar(String avatarKey);

}
