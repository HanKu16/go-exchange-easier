package com.go_exchange_easier.backend.chat.api;

import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface ChatFacade {

    boolean isUserMemberOfRoom(UUID userId, UUID roomId);

    SimplePage<ChatMessage> getPageOfMessages(
            UUID userId,
            UUID roomId,
            Pageable pageable
    );

}
