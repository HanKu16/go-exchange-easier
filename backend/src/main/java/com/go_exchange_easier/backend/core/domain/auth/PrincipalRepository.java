package com.go_exchange_easier.backend.core.domain.auth;

import com.go_exchange_easier.backend.core.domain.auth.entity.Principal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, UUID> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<Principal> findByUsername(String username);

    boolean existsByUsername(String username);

}
