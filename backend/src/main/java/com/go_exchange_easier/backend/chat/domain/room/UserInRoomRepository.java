package com.go_exchange_easier.backend.chat.domain.room;

import com.go_exchange_easier.backend.chat.domain.room.entity.UserInRoom;
import com.go_exchange_easier.backend.chat.domain.room.entity.UserInRoomId;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInRoomRepository extends JpaRepository<UserInRoom, UserInRoomId> {

    boolean existsByRoomIdAndUserId(
            UUID roomId,
            UUID userId
    );

    @Query("""
                SELECT r.userId FROM UserInRoom r
                WHERE r.room.id = :roomId
                AND r.userId != :userId
            """)
    UUID findOtherMemberId(
            @Param("roomId") UUID roomId,
            @Param("userId") UUID userId
    );

}
