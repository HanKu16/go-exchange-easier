package com.go_exchange_easier.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.go_exchange_easier.backend.model.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Short> {

    Optional<Role> findByName(String name);

}
