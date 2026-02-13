package com.go_exchange_easier.backend.chat.message.impl;

import com.go_exchange_easier.backend.chat.message.Message;
import com.go_exchange_easier.backend.chat.message.MessageRepository;
import com.go_exchange_easier.backend.chat.message.MessageService;
import com.go_exchange_easier.backend.chat.message.dto.AuthorSummary;
import com.go_exchange_easier.backend.chat.message.dto.CreateMessageRequest;
import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.chat.room.RoomRepository;
import com.go_exchange_easier.backend.chat.room.entity.Room;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.api.CoreAvatar;
import com.go_exchange_easier.backend.core.api.CoreFacade;
import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final CoreFacade coreFacade;

    @Override
    public SimplePage<MessageDetails> getPage(UUID roomId, Pageable pageable) {
        Page<Message> pageOfMessages = messageRepository.findByRoomId(roomId, pageable);
        HashMap<String, String> avatars = new HashMap<>();
        List<MessageDetails> messages = new ArrayList<>(pageOfMessages.getSize());
        for (Message message : pageOfMessages.getContent()) {
            String avatarKey = message.getAvatarKey();
            if (!avatars.containsKey(avatarKey)) {
                CoreAvatar avatar = coreFacade.getAvatar(message.getAvatarKey());
                avatars.put(avatarKey, avatarKey != null ? avatar.thumbnailUrl() : null);
            }
            messages.add(new MessageDetails(message.getId(),
                    message.getCreatedAt().toInstant(), message.getTextContent(),
                    new AuthorSummary(message.getNick(), avatars.get(avatarKey))));
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
        Room room = roomRepository.getReferenceById(roomId);
        message.setRoom(room);
        Message createdMessage;
        try {
            createdMessage = messageRepository.saveAndFlush(message);
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
                new AuthorSummary(user.getNick(), avatarUrl));
    }

}
