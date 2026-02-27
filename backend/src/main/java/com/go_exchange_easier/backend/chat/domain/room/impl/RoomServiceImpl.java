package com.go_exchange_easier.backend.chat.domain.room.impl;

import com.go_exchange_easier.backend.chat.domain.exception.MissingRoomParticipantException;
import com.go_exchange_easier.backend.chat.domain.message.Message;
import com.go_exchange_easier.backend.chat.domain.message.dto.AuthorSummary;
import com.go_exchange_easier.backend.chat.domain.message.dto.MessageSummary;
import com.go_exchange_easier.backend.chat.domain.room.RoomProjection;
import com.go_exchange_easier.backend.chat.domain.room.RoomRepository;
import com.go_exchange_easier.backend.chat.domain.room.RoomService;
import com.go_exchange_easier.backend.chat.domain.room.UserInRoomRepository;
import com.go_exchange_easier.backend.chat.domain.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.domain.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.domain.room.dto.RoomPreview;
import com.go_exchange_easier.backend.chat.domain.room.entity.Room;
import com.go_exchange_easier.backend.chat.domain.room.entity.UserInRoom;
import com.go_exchange_easier.backend.chat.domain.room.entity.UserInRoomId;
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
            boolean hasAnyUnreadMessages = hasAnyUnreadMessages(
                    room.lastMessageCreatedAt(), room.lastReadAt());
            roomsPreviews.add(new RoomPreview(room.id(), targetUser.nick(),
                    targetUser.id(), hasAnyUnreadMessages, targetUserAvatarUrl,
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

    @Override
    @Transactional
    public void updateLastReadAt(UUID roomId, int userId) {
        Room roomReference = roomRepository.getReferenceById(roomId);
        UserInRoom userInRoom = userInRoomRepository.findById(
                new UserInRoomId(roomReference, userId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room of id " + roomId + " was not found."));
        userInRoom.setLastReadAt(OffsetDateTime.now());
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
                .orElseThrow(() -> new MissingRoomParticipantException(
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

    private boolean hasAnyUnreadMessages(
            @Nullable OffsetDateTime lastMessageCreatedAt,
            @Nullable OffsetDateTime lastReadAt) {
        if (lastMessageCreatedAt == null) {
            return false;
        }
        if (lastReadAt == null) {
            return true;
        }
        return lastMessageCreatedAt.isAfter(lastReadAt);
    }

}

