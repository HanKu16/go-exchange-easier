package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.userStatus.GetUserStatusResponse;
import java.util.List;

public interface UserStatusService {

    List<GetUserStatusResponse> getAll();

}
