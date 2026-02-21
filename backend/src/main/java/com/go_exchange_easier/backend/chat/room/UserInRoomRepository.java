package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.entity.UserInRoom;
import com.go_exchange_easier.backend.chat.room.entity.UserInRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserInRoomRepository extends JpaRepository<UserInRoom, UserInRoomId> {

    @Query("""
        SELECT COUNT(r) > 0
        FROM UserInRoom r
        WHERE r.room.id = :roomId AND r.userId = :userId
    """)
    boolean isUserMemberOfRoom(@Param("roomId") UUID roomId, @Param("userId") int userId);

    @Query("""
        SELECT r.userId FROM UserInRoom r
        WHERE r.room.id = :roomId
        AND r.userId != :userId
    """)
    int findOtherMemberId(@Param("roomId") UUID roomId, @Param("userId") int userId);

}
