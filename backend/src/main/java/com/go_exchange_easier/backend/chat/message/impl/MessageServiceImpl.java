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
import com.go_exchange_easier.backend.core.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.core.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final CoreFacade coreFacade;

    @Override
    @Transactional(readOnly = true)
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
    public MessageDetails create(CreateMessageRequest request, AuthenticatedUser user) {
        Message message = new Message();
        message.setTextContent(request.textContent());
        message.setAvatarKey(user.getAvatarKey());
        message.setNick(user.getNick());
        message.setCreatedAt(OffsetDateTime.now());
        message.setDeletedAt(null);
        message.setAuthorId(user.getId());
        Room room = roomRepository.getReferenceById(request.roomId());
        message.setRoom(room);
        Message createdMessage;
        try {
            createdMessage = messageRepository.saveAndFlush(message);
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedResourceNotFoundException("Room of id " +
                    request.roomId() + " was not found.");
        }
        return new MessageDetails(createdMessage.getId(),
                createdMessage.getCreatedAt().toInstant(),
                createdMessage.getTextContent(),
                new AuthorSummary(user.getNick(), user.getAvatarKey()));
    }

    @Override
    @Transactional
    public void delete(UUID messageId, int userId) {
        int rowsUpdated = messageRepository.deleteById(
                messageId, userId, OffsetDateTime.now());
        if (rowsUpdated == 0) {
            throw new ResourceNotFoundException("Message of id " +
                    messageId + " and author if " + userId +
                    " was not found.");
        }
//        handle situation when last message in conversation is deleted
//        and it should be updated in rooms table
    }

}
