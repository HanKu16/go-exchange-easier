package com.go_exchange_easier.backend.core.api;

import java.util.Map;
import java.util.Set;

public interface CoreFacade {

    CoreUser getUser(int userId);
    Map<Integer, CoreUser> getUsers(Set<Integer> userIds);
    CoreAvatar getAvatar(String avatarKey);

}
