package com.go_exchange_easier.backend.chat.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Page<Message> findByRoomId(UUID roomId, Pageable pageable);

    @Query("""
        SELECT m FROM Message m
        WHERE m.room.id = :roomId
        ORDER BY m.createdAt DESC
        LIMIT 1
    """)
    Optional<Message> findLastMessageFromRoom(@Param("roomId") UUID roomId);

}
