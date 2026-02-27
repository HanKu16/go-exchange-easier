package com.go_exchange_easier.backend.chat.domain.message.impl;

import com.go_exchange_easier.backend.chat.domain.message.MessageApi;
import com.go_exchange_easier.backend.chat.domain.message.MessageService;
import com.go_exchange_easier.backend.chat.domain.message.dto.CreateMessageRequest;
import com.go_exchange_easier.backend.chat.domain.message.dto.MessageDetails;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService messageService;

    @Override
    public ResponseEntity<SimplePage<MessageDetails>> getPage(UUID roomId,
            Pageable pageable, AuthenticatedUser authenticatedUser) {
        SimplePage<MessageDetails> page = messageService
                .getPage(roomId, authenticatedUser.getId(), pageable);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<MessageDetails> create(
            UUID roomId, CreateMessageRequest request,
            AuthenticatedUser authenticatedUser) {
        MessageDetails message = messageService.create(
                roomId, request, authenticatedUser);
        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<Void> delete(UUID roomId, UUID messageId,
            AuthenticatedUser authenticatedUser) {
        messageService.delete(messageId, roomId, authenticatedUser.getId());
        return ResponseEntity.noContent()
                .build();
    }

}
