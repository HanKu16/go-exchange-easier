package com.go_exchange_easier.backend.chat.message.impl;

import com.go_exchange_easier.backend.chat.message.Message;
import com.go_exchange_easier.backend.chat.message.MessageRepository;
import com.go_exchange_easier.backend.chat.message.MessageService;
import com.go_exchange_easier.backend.chat.message.dto.AuthorDetails;
import com.go_exchange_easier.backend.chat.message.dto.CreateMessageRequest;
import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.chat.room.RoomService;
import com.go_exchange_easier.backend.chat.room.entity.Room;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.api.CoreFacade;
import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.core.api.CoreUser;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomService roomService;
    private final CoreFacade coreFacade;

    @Override
    public SimplePage<MessageDetails> getPage(
            UUID roomId, int userId, Pageable pageable) {
        if (!roomService.isUserMemberOfRoom(roomId, userId)) {
            throw new AuthorizationDeniedException("Authenticated user " +
                    "is not member of room that he is trying to access.");
        }
        Page<Message> pageOfMessages = messageRepository.findByRoomId(roomId, pageable);
        HashMap<Integer, CoreUser> authors = new HashMap<>();
        List<MessageDetails> messages = new ArrayList<>(pageOfMessages.getSize());
        for (Message message : pageOfMessages.getContent()) {
            int authorId = message.getAuthorId();
            if (!authors.containsKey(authorId)) {
                CoreUser user = coreFacade.getUser(authorId);
                authors.put(authorId, user);
            }
            CoreUser author = authors.get(authorId);
            String avatarUrl = author.avatar() != null ?
                    author.avatar().thumbnailUrl() : null;
            messages.add(new MessageDetails(message.getId(),
                    message.getCreatedAt().toInstant(),
                    message.getTextContent(),
                    new AuthorDetails(message.getAuthorId(),
                            author.nick(), avatarUrl )));
        }
        return SimplePage.of(messages, pageOfMessages.getNumber(),
                pageOfMessages.getSize(), pageOfMessages.getTotalElements());
    }

    @Override
    @Transactional
    public MessageDetails create(UUID roomId,
            CreateMessageRequest request,
            AuthenticatedUser user) {
        Message message = new Message();
        message.setTextContent(request.textContent());
        message.setAvatarKey(user.getAvatarKey());
        message.setNick(user.getNick());
        message.setCreatedAt(OffsetDateTime.now());
        message.setDeletedAt(null);
        message.setAuthorId(user.getId());
        Room room = roomService.getReference(roomId);
        message.setRoom(room);
        Message createdMessage;
        try {
            createdMessage = messageRepository.saveAndFlush(message);
            roomService.updateLastMessage(roomId, createdMessage);
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedResourceNotFoundException("Room of id " +
                    roomId + " was not found.");
        }
        String avatarUrl = null;
        if (user.getAvatarKey() != null) {
            avatarUrl = coreFacade.getAvatar(user.getAvatarKey()).thumbnailUrl();
        }
        return new MessageDetails(createdMessage.getId(),
                createdMessage.getCreatedAt().toInstant(),
                createdMessage.getTextContent(),
                new AuthorDetails(createdMessage.getAuthorId(),
                        user.getNick(), avatarUrl));
    }

}
