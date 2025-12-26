package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.summary.UniversityMajorSummary;
import java.util.List;

public interface UniversityMajorService {

    List<UniversityMajorSummary> getAll();

}
