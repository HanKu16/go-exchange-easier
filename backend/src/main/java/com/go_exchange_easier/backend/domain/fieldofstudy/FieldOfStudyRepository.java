package com.go_exchange_easier.backend.domain.fieldofstudy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOfStudyRepository extends
        JpaRepository<FieldOfStudy, Short> { }
