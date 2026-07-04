package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.core.domain.user.User;
import com.go_exchange_easier.backend.core.domain.user.UserInitializer;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInitializerImpl implements UserInitializer {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void initialize(
            UUID id,
            String nick,
            OffsetDateTime createdAt
    ) {
        User user = new User();
        user.setId(id);
        user.setNick(nick);
        user.setCreatedAt(createdAt);
        user.setBlocked(false);
        userRepository.save(user);
    }

}
