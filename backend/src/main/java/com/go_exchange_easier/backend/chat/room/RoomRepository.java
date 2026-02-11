package com.go_exchange_easier.backend.chat.room;

import com.go_exchange_easier.backend.chat.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends
        JpaRepository<Room, UUID> {

    @Query(value = """
        SELECT
            r.room_id,
            r.last_message_at,
            r.last_message_text_content,
            r.last_message_author_nick,
            r.last_message_author_avatar_key,
            others.user_id AS other_user_id
        FROM chat.user_in_rooms me
        JOIN chat.user_in_rooms others ON me.room_id = others.room_id
        JOIN chat.rooms r ON r.room_id = me.room_id
        WHERE r.last_message_at IS NOT NULL AND me.user_id = :userId AND others.user_id != :userId
        ORDER BY r.last_message_at DESC
        LIMIT :limit
        OFFSET :offset
    """, nativeQuery = true)
    List<Object[]> findRoomWithOtherParticipant(@Param("userId") int userId,
            @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = """
        SELECT
            COUNT(*)
        FROM chat.user_in_rooms u
        JOIN chat.rooms r ON r.room_id = u.room_id
        WHERE u.user_id = :userId AND r.last_message_at IS NOT NULL
    """, nativeQuery = true)
    int countUserRoomsThatContainsAnyMessage(@Param("userId") int userId);

    @Query(value = """
        SELECT
            r.room_id
        FROM chat.user_in_rooms me
        JOIN chat.user_in_rooms others ON me.room_id = others.room_id
        JOIN chat.rooms r ON r.room_id = me.room_id
        WHERE me.user_id = :userId AND others.user_id = :targetUserId
        AND me.user_id != others.user_id
    """, nativeQuery = true)
    Optional<UUID> findRoomIdWithParticipants(
            @Param("userId") int userId,
            @Param("targetUserId") int targetUserId);

}
