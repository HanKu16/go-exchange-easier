package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.room.dto.RoomDetails;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.UUID;

public interface RoomService {

    SimplePage<RoomSummary> getUserRooms(int userId, int page, int size);
    RoomDetails getOrCreate(int userId, CreateRoomRequest request);
    RoomDetails getById(UUID roomId, int signedInUserId);

}
