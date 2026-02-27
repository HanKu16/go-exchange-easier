package com.go_exchange_easier.backend.chat.domain.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Page<Message> findByRoomId(UUID roomId, Pageable pageable);
    Optional<Message> findTopByRoomIdOrderByCreatedAtDesc(UUID roomId);
    @EntityGraph(attributePaths = {"room"})
    Optional<Message> findWithRoomById(UUID messageId);

}
