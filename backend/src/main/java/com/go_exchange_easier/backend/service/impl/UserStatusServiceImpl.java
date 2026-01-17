package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.summary.UserStatusSummary;
import com.go_exchange_easier.backend.model.UserStatus;
import com.go_exchange_easier.backend.repository.UserStatusRepository;
import com.go_exchange_easier.backend.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="user_statuses", key="'all'")
    public List<UserStatusSummary> getAll() {
        List<UserStatus> statuses = userStatusRepository.findAll();
        return statuses.stream()
                .map(s -> new UserStatusSummary(s.getId(), s.getName()))
                .toList();
    }

}
