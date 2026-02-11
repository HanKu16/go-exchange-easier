package com.go_exchange_easier.backend.chat.room.impl;

import com.go_exchange_easier.backend.chat.room.RoomApi;
import com.go_exchange_easier.backend.chat.room.RoomService;
import com.go_exchange_easier.backend.chat.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.room.dto.RoomDetails;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @Override
    public ResponseEntity<SimplePage<RoomSummary>> getUserRoomsPage(
            AuthenticatedUser authenticatedUser,
            Integer page, Integer size) {
        SimplePage<RoomSummary> rooms = roomService.getUserRooms(
                authenticatedUser.getId(), page, size);
        return ResponseEntity.ok(rooms);
    }

    @Override
    public ResponseEntity<RoomDetails> getOrCreate(
            CreateRoomRequest request,
            AuthenticatedUser authenticationUser) {
        RoomDetails room = roomService.getOrCreate(
                authenticationUser.getId(), request);
        return ResponseEntity.ok(room);
    }

}
