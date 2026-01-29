package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.domain.auth.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {

    @Cacheable(value="roles", key="#name")
    Optional<Role> findByName(String name);

}
