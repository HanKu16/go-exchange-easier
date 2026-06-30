package com.go_exchange_easier.backend.core.api;

import com.go_exchange_easier.backend.core.domain.user.UserReadService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreFacadeImpl implements CoreFacade {

    private final UserReadService userReadService;
    private final AvatarService avatarService;

    @Override
    public CoreUser getUserById(int userId) {
        return userReadService.getUser(userId);
    }

    @Override
    public Map<Integer, CoreUser> getUsersByIds(Set<Integer> userIds) {
        return userReadService.getUsers(userIds);
    }

    @Override
    public CoreAvatar getAvatar(String avatarKey) {
        AvatarUrlSummary avatarUrl = avatarService.getUrl(avatarKey);
        return new CoreAvatar(avatarUrl.original(), avatarUrl.thumbnail());
    }

}
