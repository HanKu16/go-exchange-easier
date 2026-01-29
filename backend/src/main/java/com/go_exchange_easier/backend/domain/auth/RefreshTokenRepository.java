package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends
        JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByHashedToken(String hashedToken);

    @Modifying
    @Query("UPDATE RefreshToken t SET t.isRevoked = true")
    int revokeByHashedToken(String hashedToken);

}
