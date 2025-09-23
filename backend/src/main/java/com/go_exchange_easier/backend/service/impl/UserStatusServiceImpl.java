package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.userStatus.GetUserStatusResponse;
import com.go_exchange_easier.backend.model.UserStatus;
import com.go_exchange_easier.backend.repository.UserStatusRepository;
import com.go_exchange_easier.backend.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    @Override
    public List<GetUserStatusResponse> getAll() {
        List<UserStatus> statuses = userStatusRepository.findAll();
        return statuses.stream()
                .map(s -> new GetUserStatusResponse(s.getId(), s.getName()))
                .toList();
    }

}
