package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.universityMajor.GetUniversityMajorResponse;
import java.util.List;

public interface UniversityMajorService {

    List<GetUniversityMajorResponse> getAll();

}
