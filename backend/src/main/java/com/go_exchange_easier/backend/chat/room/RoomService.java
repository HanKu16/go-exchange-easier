package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import com.go_exchange_easier.backend.common.dto.SimplePage;

public interface RoomService {

    SimplePage<RoomSummary> getUserRooms(int userId, int page, int size);

}
