package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.domain.auth.dto.UserCredentialsDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    @EntityGraph(attributePaths = {"user", "roles"})
    Optional<UserCredentials> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT new com.go_exchange_easier.backend.domain.auth.dto.UserCredentialsDto(" +
            "uc.id, uc.username, uc.password, uc.isEnabled) " +
            "FROM UserCredentials uc " +
            "WHERE uc.username = :username")
    Optional<UserCredentialsDto> findDtoByUsername(String username);

}
