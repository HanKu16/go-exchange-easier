package com.go_exchange_easier.backend.chat.message;

import com.go_exchange_easier.backend.chat.message.dto.AuthorSummary;
import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.api.CoreAvatar;
import com.go_exchange_easier.backend.core.api.CoreFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
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
                pageOfMessages.getSize(), pageOfMessages.getNumberOfElements());
    }

}
