package com.go_exchange_easier.backend.chat.room.impl;

import com.go_exchange_easier.backend.chat.message.MessageService;
import com.go_exchange_easier.backend.chat.message.dto.AuthorSummary;
import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.chat.message.dto.MessageSummary;
import com.go_exchange_easier.backend.chat.room.RoomRepository;
import com.go_exchange_easier.backend.chat.room.RoomService;
import com.go_exchange_easier.backend.chat.room.UserInRoomRepository;
import com.go_exchange_easier.backend.chat.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.room.dto.RoomDetails;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.room.entity.Room;
import com.go_exchange_easier.backend.chat.room.entity.UserInRoom;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.api.CoreFacade;
import com.go_exchange_easier.backend.core.api.CoreUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    private final UserInRoomRepository userInRoomRepository;
    private final RoomRepository roomRepository;
    private final MessageService messageService;
    private final CoreFacade coreFacade;

    @Override
    public SimplePage<RoomSummary> getUserRooms(int userId, int page, int size) {
        int offset = page * size;
        int totalElements = roomRepository.countUserRoomsThatContainsAnyMessage(userId);
        List<Object[]> rows = roomRepository.findRoomWithOtherParticipant(
                userId, size, offset);
        Set<Integer> otherUserIds = new HashSet<>(size);
        for (Object[] row : rows) {
            Integer otherUserId = (Integer) row[5];
            otherUserIds.add(otherUserId);
        }
        Map<Integer, CoreUser> users = coreFacade.getUsers(otherUserIds);
        List<RoomSummary> rooms = new ArrayList<>(10);
        for (Object[] row : rows) {
            UUID roomId = (UUID) row[0];
            Instant lastMessageAt = handleCastAndNullCheck(
                    row[1], Instant.class);
            String lastMessageTextContent = handleCastAndNullCheck(row[2], String.class);
            String lastMessageAuthorNick = handleCastAndNullCheck(row[3], String.class);
            String lastMessageAuthorAvatarKey = handleCastAndNullCheck(
                    row[4], String.class);
            Integer otherParticipantUserId = (Integer) row[5];
            CoreUser user = users.get(otherParticipantUserId);
            rooms.add(new RoomSummary(roomId, user.nick(),
                    user.avatar() != null ? user.avatar().thumbnailUrl() : null,
                    new MessageSummary(lastMessageAt, lastMessageTextContent,
                            new AuthorSummary(lastMessageAuthorNick,
                                    lastMessageAuthorAvatarKey))));
        }
        return SimplePage.of(rooms, page, size, (long) totalElements);
    }

    @Override
    @Transactional
    public RoomDetails getOrCreate(int userId, CreateRoomRequest request) {
        Optional<UUID> optionalRoomId = roomRepository
                .findRoomIdWithParticipants(userId, request.targetUserId());
        UUID roomId;
        SimplePage<MessageDetails> pageOfMessages = SimplePage.empty(30);
        if (optionalRoomId.isPresent()) {
            roomId = optionalRoomId.get();
            pageOfMessages = messageService.getPage(roomId, PageRequest.of(
                    0, 30, Sort.by(Sort.Direction.DESC, "createdAt")));
        } else {
            Room createdRoom = createRoom(userId, request.targetUserId());
            roomId = createdRoom.getId();
        }
        CoreUser targetUser = coreFacade.getUser(request.targetUserId());
        return new RoomDetails(roomId, targetUser.nick(),
                targetUser.avatar() != null ?
                        targetUser.avatar().thumbnailUrl() : null,
                pageOfMessages);
    }

    private Room createRoom(int userId, int targetUserId) {
        Room createdRoom = roomRepository.save(new Room());
        UserInRoom creatorUserInRoom = new UserInRoom();
        creatorUserInRoom.setRoom(createdRoom);
        creatorUserInRoom.setUserId(userId);
        UserInRoom targetUserInRoom = new UserInRoom();
        targetUserInRoom.setRoom(createdRoom);
        targetUserInRoom.setUserId(targetUserId);
        userInRoomRepository.saveAll(List.of(targetUserInRoom, creatorUserInRoom));
        return createdRoom;
    }

    private <T> T handleCastAndNullCheck(Object obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        throw new ClassCastException("Cast error, expected type was: " +
                clazz.getSimpleName() + ", but get: " +
                obj.getClass().getSimpleName());
    }

}

