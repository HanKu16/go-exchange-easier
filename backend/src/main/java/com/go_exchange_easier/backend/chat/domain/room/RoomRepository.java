package com.go_exchange_easier.backend.chat.domain.room;

import com.go_exchange_easier.backend.chat.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends
        JpaRepository<Room, UUID> {

    @Query(value = """
        SELECT new com.go_exchange_easier.backend.chat.domain.room.RoomProjection(
            r.id,
            otherUser.userId,
            m.id,
            m.createdAt,
            m.textContent,
            m.authorId
        )
        FROM Room r
        JOIN r.users me
        JOIN r.users otherUser
        JOIN r.lastMessage m
        WHERE me.userId = :userId
            AND otherUser.userId != :userId
        ORDER BY m.createdAt DESC
    """, countQuery = """
        SELECT COUNT(r)
        FROM Room r
        JOIN r.users me
        WHERE me.userId = :userId
        AND r.lastMessage IS NOT NULL
    """)
    Page<RoomProjection> findRoomsProjectionByUserId(
            @Param("userId") int userId,
            Pageable pageable
    );

    @Query("""
        SELECT
            r
        FROM Room r
        JOIN r.users u1
        JOIN r.users u2
        WHERE u1.userId = :userId
        AND u2.userId = :targetUserId
    """)
    Optional<Room> findPrivateRoomWithUsers(
            @Param("userId") int userId,
            @Param("targetUserId") int targetUserId);

    @EntityGraph(attributePaths = {"users"})
    Optional<Room> findPrivateRoomWithUsersById(UUID roomId);

}
