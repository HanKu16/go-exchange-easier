package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.domain.user.dto.BasicUser;
import java.util.UUID;

public interface BasicUserProvider {

    BasicUser getById(UUID id);

}
