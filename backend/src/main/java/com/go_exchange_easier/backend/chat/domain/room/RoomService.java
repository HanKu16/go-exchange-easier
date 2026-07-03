package com.go_exchange_easier.backend.chat.domain.room;

import com.go_exchange_easier.backend.chat.domain.message.Message;
import com.go_exchange_easier.backend.chat.domain.room.dto.CreateRoomRequest;
import com.go_exchange_easier.backend.chat.domain.room.dto.RoomPreview;
import com.go_exchange_easier.backend.chat.domain.room.dto.RoomSummary;
import com.go_exchange_easier.backend.chat.domain.room.entity.Room;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import java.util.UUID;

public interface RoomService {

    SimplePage<RoomPreview> getUserRooms(
            UUID userId,
            int page,
            int size
    );

    RoomSummary getOrCreate(
            UUID userId,
            CreateRoomRequest request
    );

    RoomSummary getById(
            UUID roomId,
            UUID signedInUserId
    );

    boolean isUserMemberOfRoom(
            UUID roomId,
            UUID userId
    );

    Room getReference(UUID roomId);

    void updateLastMessage(
            UUID roomId,
            Message message
    );

    void updateLastReadAt(
            UUID roomId,
            UUID userId
    );

}
