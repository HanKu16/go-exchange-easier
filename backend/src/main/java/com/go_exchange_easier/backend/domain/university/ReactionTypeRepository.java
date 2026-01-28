package com.go_exchange_easier.backend.domain.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionTypeRepository extends
        JpaRepository<ReactionType, Short> { }
