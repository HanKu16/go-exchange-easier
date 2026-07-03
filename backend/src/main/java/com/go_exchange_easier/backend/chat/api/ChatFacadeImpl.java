package com.go_exchange_easier.backend.chat.api;

import com.go_exchange_easier.backend.chat.domain.message.MessageService;
import com.go_exchange_easier.backend.chat.domain.message.dto.MessageDetails;
import com.go_exchange_easier.backend.chat.domain.room.RoomService;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatFacadeImpl implements ChatFacade {

    private final MessageService messageService;
    private final RoomService roomService;

    @Override
    public boolean isUserMemberOfRoom(
            UUID userId,
            UUID roomId
    ) {
        return roomService.isUserMemberOfRoom(roomId, userId);
    }

    public SimplePage<ChatMessage> getPageOfMessages(
            UUID userId,
            UUID roomId,
            Pageable pageable
    ) {
        SimplePage<MessageDetails> pageOfMessages = messageService.getPage(roomId, userId, pageable);
        List<ChatMessage> transformedMessages = pageOfMessages.content()
                .stream()
                .map(this::mapToChatMessage)
                .toList();
        return new SimplePage<>(
                transformedMessages,
                pageOfMessages.pageNumber(),
                pageOfMessages.pageSize(),
                pageOfMessages.totalElements(),
                pageOfMessages.totalPages()
        );
    }

    private ChatMessage mapToChatMessage(MessageDetails message) {
        return new ChatMessage(
                message.id(),
                message.createdAt(),
                message.textContent(),
                new ChatMessageAuthor(
                        message.author().id(),
                        message.author().nick()
                )
        );
    }

}
