package com.go_exchange_easier.backend.domain.user.status.impl;

import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.user.status.UserStatusApi;
import com.go_exchange_easier.backend.domain.user.status.UserStatusService;
import com.go_exchange_easier.backend.domain.user.status.UserStatusSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserStatusController implements UserStatusApi {

    private final UserStatusService userStatusService;

    @Override
    public ResponseEntity<Listing<UserStatusSummary>> getAll() {
        List<UserStatusSummary> statuses = userStatusService.getAll();
        return ResponseEntity.ok(Listing.of(statuses));
    }

}
