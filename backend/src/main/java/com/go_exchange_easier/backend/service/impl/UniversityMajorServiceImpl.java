package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.universityMajor.GetUniversityMajorResponse;
import com.go_exchange_easier.backend.model.UniversityMajor;
import com.go_exchange_easier.backend.repository.UniversityMajorRepository;
import com.go_exchange_easier.backend.service.UniversityMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityMajorServiceImpl implements UniversityMajorService {

    private final UniversityMajorRepository universityMajorRepository;

    @Override
    public List<GetUniversityMajorResponse> getAll() {
        List<UniversityMajor> majors = universityMajorRepository.findAll();
        return majors.stream()
                .map(m -> new GetUniversityMajorResponse(m.getId(), m.getName()))
                .toList();
    }

}
