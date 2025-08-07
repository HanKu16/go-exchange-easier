package com.go_exchange_easier.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.go_exchange_easier.backend.model.Role;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {

    Optional<Role> findByName(String name);

}
