package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.dto.RoomSummary;
import java.util.List;

public interface RoomService {

    List<RoomSummary> getUserRooms(int userId, int page, int size);

}
