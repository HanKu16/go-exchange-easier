package com.go_exchange_easier.backend.core.api;

import com.go_exchange_easier.backend.core.domain.user.UserReadService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CoreFacadeImpl implements CoreFacade {

    private final UserReadService userReadService;
    private final AvatarService avatarService;

    @Override
    public CoreUser getUser(int userId) {
        return userReadService.getUser(userId);
    }

    @Override
    public Map<Integer, CoreUser> getUsers(Set<Integer> userIds) {
        return userReadService.getUsers(userIds);
    }

    @Override
    public CoreAvatar getAvatar(String avatarKey) {
        AvatarUrlSummary avatarUrl = avatarService.getUrl(avatarKey);
        return new CoreAvatar(avatarUrl.original(), avatarUrl.thumbnail());
    }

}
