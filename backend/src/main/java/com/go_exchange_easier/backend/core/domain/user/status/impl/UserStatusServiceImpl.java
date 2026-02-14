package com.go_exchange_easier.backend.core.domain.user.status.impl;

import com.go_exchange_easier.backend.core.domain.user.status.UserStatus;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusRepository;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusService;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    @Override
    @Cacheable(value="user_statuses", key="'all'")
    public List<UserStatusSummary> getAll() {
        List<UserStatus> statuses = userStatusRepository.findAll();
        return statuses.stream()
                .map(s -> new UserStatusSummary(s.getId(), s.getName()))
                .toList();
    }

}
