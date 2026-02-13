package com.go_exchange_easier.backend.chat.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Page<Message> findByRoomId(UUID roomId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Message m SET m.deletedAt = :now " +
            "WHERE m.id = :messageId AND m.authorId = :userId")
    int deleteById(@Param("messageId") UUID messageId, @Param("userId") int userId,
        @Param("now") OffsetDateTime now);

}
