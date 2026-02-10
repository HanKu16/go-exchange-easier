package com.go_exchange_easier.backend.core.domain.auth;

import com.go_exchange_easier.backend.core.domain.auth.entity.UserCredentials;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    @EntityGraph(attributePaths = {"user", "roles"})
    Optional<UserCredentials> findByUsername(String username);
    boolean existsByUsername(String username);

}
