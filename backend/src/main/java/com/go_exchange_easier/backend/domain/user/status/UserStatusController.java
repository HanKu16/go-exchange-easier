package com.go_exchange_easier.backend.domain.user.status;

import com.go_exchange_easier.backend.domain.user.annotations.userstatus.GetAllApiDocs;
import com.go_exchange_easier.backend.common.dto.Listing;
import com.go_exchange_easier.backend.domain.user.dto.UserStatusSummary;
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
