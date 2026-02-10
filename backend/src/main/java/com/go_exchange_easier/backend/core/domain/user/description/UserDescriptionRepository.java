package com.go_exchange_easier.backend.core.domain.user.description;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface UserDescriptionRepository extends JpaRepository<UserDescription, Integer> {

    Optional<UserDescription> findByUserId(int userId);

    @Modifying
    @Query("UPDATE UserDescription ud " +
            "SET ud.textContent = :textContent, ud.updatedAt = :updatedAt " +
            "WHERE ud.user.id = :userId")
    int updateByUserId(@Param("userId") int userId,
            @Param("textContent") String textContent,
            @Param("updatedAt") OffsetDateTime updatedAt);

}
