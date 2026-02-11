package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.entity.UserInRoom;
import com.go_exchange_easier.backend.chat.room.entity.UserInRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInRoomRepository extends
        JpaRepository<UserInRoom, UserInRoomId> { }
