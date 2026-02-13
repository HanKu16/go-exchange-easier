package com.go_exchange_easier.backend.chat.message.impl;

import com.go_exchange_easier.backend.chat.message.MessageApi;
import com.go_exchange_easier.backend.chat.message.MessageService;
import com.go_exchange_easier.backend.chat.message.dto.CreateMessageRequest;
import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService messageService;

    @Override
    public ResponseEntity<MessageDetails> create(
            CreateMessageRequest request,
            AuthenticatedUser authenticatedUser) {
        MessageDetails message = messageService.create(request, authenticatedUser);
        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<Void> delete(UUID messageId,
            AuthenticatedUser authenticatedUser) {
        messageService.delete(messageId, authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }

}
