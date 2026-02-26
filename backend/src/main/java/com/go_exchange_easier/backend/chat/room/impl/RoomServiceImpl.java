package com.go_exchange_easier.backend.chat.room.impl;

import com.go_exchange_easier.backend.chat.message.Message;
import com.go_exchange_easier.backend.chat.message.dto.AuthorSummary;
import com.go_exchange_easier.backend.chat.message.dto.MessageSummary;
import com.go_exchange_easier.backend.chat.room.*;
import com.go_exchange_easier.backend.chat.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.room.dto.RoomPreview;
import com.go_exchange_easier.backend.chat.room.entity.Room;
import com.go_exchange_easier.backend.chat.room.entity.UserInRoom;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.api.CoreFacade;
import com.go_exchange_easier.backend.core.api.CoreUser;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    private final UserInRoomRepository userInRoomRepository;
    private final RoomRepository roomRepository;
    private final CoreFacade coreFacade;

    @Override
    public SimplePage<RoomPreview> getUserRooms(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomProjection> pageOfRooms = roomRepository
                .findRoomsProjectionByUserId(userId, pageable);
        Set<Integer> usersIds = extractUsersIds(pageOfRooms);
        Map<Integer, CoreUser> users = coreFacade.getUsers(usersIds);
        List<RoomPreview> roomsPreviews = new ArrayList<>(size);
        for (RoomProjection room : pageOfRooms.getContent()) {
            CoreUser targetUser = users.get(room.targetUserId());
            String targetUserAvatarUrl = targetUser.avatar() != null ?
                    targetUser.avatar().thumbnailUrl() : null;
            CoreUser lastMessageAuthor = users.get(room.lastMessageAuthorId());
            String lastMessageAuthorAvatarUrl = lastMessageAuthor.avatar() != null ?
                    lastMessageAuthor.avatar().thumbnailUrl() : null;
            roomsPreviews.add(new RoomPreview(room.id(), targetUser.nick(),
                    targetUser.id(), targetUserAvatarUrl,
                    new MessageSummary(room.lastMessageCreatedAt().toInstant(),
                            room.lastMessageTextContent(),
                            new AuthorSummary(lastMessageAuthor.nick(),
                                    lastMessageAuthorAvatarUrl))));
        }
        return SimplePage.of(roomsPreviews, page, size, pageOfRooms.getTotalElements());
    }

    @Override
    @Transactional
    public RoomSummary getOrCreate(int userId, CreateRoomRequest request) {
        Optional<Room> optionalRoom = roomRepository.findPrivateRoomWithUsers(
                userId, request.targetUserId());
        UUID roomId;
        if (optionalRoom.isPresent()) {
            roomId = optionalRoom.get().getId();
        } else {
            Room createdRoom = createRoom(userId, request.targetUserId());
            roomId = createdRoom.getId();
        }
        CoreUser targetUser = coreFacade.getUser(request.targetUserId());
        return new RoomSummary(roomId, targetUser.nick(), targetUser.id(),
                targetUser.avatar() != null ?
                        targetUser.avatar().thumbnailUrl() : null);
    }

    @Override
    @Transactional
    public RoomSummary getById(UUID roomId, int signedInUserId) {
        Room room = roomRepository
                .findPrivateRoomWithUsersById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room of id " + roomId + " was not found."));
        if (!isUserMemberOfRoom(signedInUserId, room.getUsers())) {
            throw new ResourceNotFoundException(
                    "Room of id " + roomId + " was not found.");
        }
        int targetUserId = getTargetUserId(signedInUserId, room.getUsers());
        CoreUser targetUser = coreFacade.getUser(targetUserId);
        return new RoomSummary(roomId, targetUser.nick(),
                targetUserId, targetUser.avatar() != null ?
                        targetUser.avatar().thumbnailUrl() : null);
    }

    @Override
    public boolean isUserMemberOfRoom(UUID roomId, int userId) {
        return userInRoomRepository.existsByRoomIdAndUserId(roomId, userId);
    }

    @Override
    public Room getReference(UUID roomId) {
        return roomRepository.getReferenceById(roomId);
    }

    @Override
    @Transactional
    public void updateLastMessage(UUID roomId, @Nullable Message message) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room of id " + roomId + " was not found."));
        room.setLastMessage(message);
        roomRepository.save(room);
    }

    private Room createRoom(int userId, int targetUserId) {
        Room newRoom = new Room();
        OffsetDateTime now = OffsetDateTime.now();
        newRoom.setCreatedAt(now);
        Room createdRoom = roomRepository.save(newRoom);
        UserInRoom creatorUserInRoom = new UserInRoom();
        creatorUserInRoom.setRoom(createdRoom);
        creatorUserInRoom.setJoinedAt(now);
        creatorUserInRoom.setUserId(userId);
        UserInRoom targetUserInRoom = new UserInRoom();
        targetUserInRoom.setRoom(createdRoom);
        targetUserInRoom.setJoinedAt(now);
        targetUserInRoom.setUserId(targetUserId);
        userInRoomRepository.saveAll(List.of(targetUserInRoom, creatorUserInRoom));
        return createdRoom;
    }

    private boolean isUserMemberOfRoom(int userId, Set<UserInRoom> usersInRoom) {
        return usersInRoom
                .stream()
                .anyMatch(u -> u.getUserId() == userId);
    }

    private int getTargetUserId(int signedInUserId, Set<UserInRoom> usersInRoom) {
        return usersInRoom
                .stream()
                .filter(u -> u.getUserId() != signedInUserId)
                .findFirst()
                .map(UserInRoom::getUserId)
                .orElseThrow(() -> new MissingChatParticipantException(
                        "Chat room supposed to have another user but has not."));
    }

    private Set<Integer> extractUsersIds(Page<RoomProjection> pageOfRooms) {
        Set<Integer> targetUsersIds = pageOfRooms.getContent()
                .stream()
                .map(RoomProjection::targetUserId)
                .collect(Collectors.toSet());
        Set<Integer> messagesAuthorsIds = pageOfRooms.getContent()
                .stream()
                .map(RoomProjection::lastMessageAuthorId)
                .collect(Collectors.toSet());
        return Stream.concat(targetUsersIds.stream(), messagesAuthorsIds.stream())
                .collect(Collectors.toSet());
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

