package com.go_exchange_easier.backend.core.api;

import java.util.Map;
import java.util.Set;

public interface CoreFacade {

    CoreUser getUserById(int userId);

    Map<Integer, CoreUser> getUsersByIds(Set<Integer> userIds);

    CoreAvatar getAvatar(String avatarKey);

}
