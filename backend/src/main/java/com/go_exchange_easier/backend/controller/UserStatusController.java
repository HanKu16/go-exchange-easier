package com.go_exchange_easier.backend.controller;

import com.go_exchange_easier.backend.annoations.docs.userStatus.GetAllApiDocs;
import com.go_exchange_easier.backend.dto.common.Listing;
import com.go_exchange_easier.backend.dto.summary.UserStatusSummary;
import com.go_exchange_easier.backend.service.UserStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/userStatuses")
@RequiredArgsConstructor
@Tag(name = "User Status", description = "Operations related to user statuses.")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @GetMapping
    @GetAllApiDocs
    public ResponseEntity<Listing<UserStatusSummary>> getAll() {
        List<UserStatusSummary> statuses = userStatusService.getAll();
        return ResponseEntity.ok(Listing.of(statuses));
    }

}
