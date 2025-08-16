package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.dto.auth.UserCredentialsDto;
import com.go_exchange_easier.backend.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    Optional<UserCredentials> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT new com.go_exchange_easier.backend.dto.auth.UserCredentialsDto(" +
            "uc.id, uc.username, uc.password, uc.isEnabled) " +
            "FROM UserCredentials uc " +
            "WHERE uc.username = :username")
    Optional<UserCredentialsDto> findDtoByUsername(String username);

}
