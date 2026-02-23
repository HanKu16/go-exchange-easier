package com.go_exchange_easier.backend.chat.room.impl;

import com.go_exchange_easier.backend.chat.room.RoomApi;
import com.go_exchange_easier.backend.chat.room.RoomService;
import com.go_exchange_easier.backend.chat.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.room.dto.RoomPreviewSummary;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @Override
    public ResponseEntity<SimplePage<RoomPreviewSummary>> getUserRoomsPage(
            AuthenticatedUser authenticatedUser,
            Integer page, Integer size) {
        SimplePage<RoomPreviewSummary> rooms = roomService.getUserRooms(
                authenticatedUser.getId(), page, size);
        return ResponseEntity.ok(rooms);
    }

    @Override
    public ResponseEntity<RoomSummary> getOrCreate(
            CreateRoomRequest request,
            AuthenticatedUser authenticationUser) {
        RoomSummary room = roomService.getOrCreate(
                authenticationUser.getId(), request);
        return ResponseEntity.ok(room);
    }

    @Override
    public ResponseEntity<RoomSummary> getById(
            UUID roomId,
            AuthenticatedUser authenticationUser) {
        RoomSummary room = roomService.getById(
                roomId, authenticationUser.getId());
        return ResponseEntity.ok(room);
    }

}
