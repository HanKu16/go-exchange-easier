package com.go_exchange_easier.backend.chat.room.impl;

import com.go_exchange_easier.backend.chat.room.RoomApi;
import com.go_exchange_easier.backend.chat.room.RoomService;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @Override
    public ResponseEntity<List<RoomSummary>> getUserRoomsPage(
            AuthenticatedUser authenticatedUser,
            Integer page, Integer size) {
        List<RoomSummary> rooms = roomService.getUserRooms(
                authenticatedUser.getId(), page, size);
        return ResponseEntity.ok(rooms);
    }

}
