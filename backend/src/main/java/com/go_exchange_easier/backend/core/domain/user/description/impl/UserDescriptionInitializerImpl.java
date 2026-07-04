package com.go_exchange_easier.backend.core.domain.user.description.impl;

import com.go_exchange_easier.backend.core.domain.user.description.UserDescription;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionInitializer;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDescriptionInitializerImpl implements UserDescriptionInitializer {

    private final UserDescriptionRepository userDescriptionRepository;

    @Override
    @Transactional
    public void initialize(UUID id) {
        UserDescription userDescription = new UserDescription();
        userDescription.setId(id);
        userDescription.setTextContent("");
        userDescriptionRepository.save(userDescription);
    }

}
