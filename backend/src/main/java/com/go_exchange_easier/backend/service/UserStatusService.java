package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.summary.UserStatusSummary;
import java.util.List;

public interface UserStatusService {

    List<UserStatusSummary> getAll();

}
