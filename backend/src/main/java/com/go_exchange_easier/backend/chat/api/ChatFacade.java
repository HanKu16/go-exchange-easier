package com.go_exchange_easier.backend.chat.api;

import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface ChatFacade {

    boolean isUserMemberOfRoom(int userId, UUID roomId);

    SimplePage<ChatMessage> getPageOfMessages(
            int userId,
            UUID roomId,
            Pageable pageable
    );

}
