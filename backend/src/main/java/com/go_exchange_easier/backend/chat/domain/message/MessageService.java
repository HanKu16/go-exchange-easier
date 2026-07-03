package com.go_exchange_easier.backend.chat.domain.message;

import com.go_exchange_easier.backend.chat.domain.message.dto.CreateMessageRequest;
import com.go_exchange_easier.backend.chat.domain.message.dto.MessageDetails;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    SimplePage<MessageDetails> getPage(
            UUID roomId,
            UUID userId,
            Pageable pageable
    );

    MessageDetails create(
            UUID roomId,
            CreateMessageRequest request,
            AuthenticatedUser signedInUser
    );

    void delete(
            UUID messageId,
            UUID roomId,
            UUID signedInUserId
    );

}
