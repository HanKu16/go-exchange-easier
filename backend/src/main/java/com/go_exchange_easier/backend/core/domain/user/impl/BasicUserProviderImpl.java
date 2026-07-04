package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.user.BasicUserProvider;
import com.go_exchange_easier.backend.core.domain.user.User;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import com.go_exchange_easier.backend.core.domain.user.dto.BasicUser;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicUserProviderImpl implements BasicUserProvider {

    private final UserRepository userRepository;

    @Override
    public BasicUser getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User of id " + id + " was not found."));
        return new BasicUser(user.getId(), user.getNick(), user.getAvatarKey(), user.getDeletedAt(), user.isBlocked());
    }

}
