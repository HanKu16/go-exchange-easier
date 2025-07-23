package com.go_exchange_easier.backend.repository;

import com.go_exchange_easier.backend.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    Optional<UserCredentials> findByUsername(String username);
    boolean existsByUsername(String username);

}
