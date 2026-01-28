package com.go_exchange_easier.backend.domain.user.status;

import com.go_exchange_easier.backend.domain.user.dto.UserStatusSummary;
import java.util.List;

public interface UserStatusService {

    List<UserStatusSummary> getAll();

}
