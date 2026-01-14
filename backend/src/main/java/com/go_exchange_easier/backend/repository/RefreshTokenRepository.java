package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends
        JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByHashedToken(String hashedToken);

}
