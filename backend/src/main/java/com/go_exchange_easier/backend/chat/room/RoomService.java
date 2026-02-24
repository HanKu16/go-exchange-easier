package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.message.Message;
import com.go_exchange_easier.backend.chat.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.room.dto.RoomPreview;
import com.go_exchange_easier.backend.chat.room.entity.Room;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.UUID;

public interface RoomService {

    SimplePage<RoomPreview> getUserRooms(int userId, int page, int size);
    RoomSummary getOrCreate(int userId, CreateRoomRequest request);
    RoomSummary getById(UUID roomId, int signedInUserId);
    boolean isUserMemberOfRoom(UUID roomId, int userId);
    Room getReference(UUID roomId);
    void updateLastMessage(UUID roomId, Message message);

}
