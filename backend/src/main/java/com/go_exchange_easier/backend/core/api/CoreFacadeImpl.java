package com.go_exchange_easier.backend.core.api;

import com.go_exchange_easier.backend.core.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreFacadeImpl implements CoreFacade {

    private final UserService userService;

    @Override
    public CoreUser getUser(int userId) {
        return userService.getUser(userId);
    }

}
