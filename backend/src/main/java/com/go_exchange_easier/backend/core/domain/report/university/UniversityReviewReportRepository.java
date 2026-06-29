package com.go_exchange_easier.backend.core.domain.report.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityReviewReportRepository extends
    JpaRepository<UniversityReviewReport, Integer> { }
