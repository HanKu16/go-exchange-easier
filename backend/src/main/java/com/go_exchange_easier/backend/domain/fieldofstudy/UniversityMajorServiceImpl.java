package com.go_exchange_easier.backend.domain.fieldofstudy;

import com.go_exchange_easier.backend.domain.fieldofstudy.dto.UniversityMajorSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityMajorServiceImpl implements UniversityMajorService {

    private final UniversityMajorRepository universityMajorRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="university_majors", key="'all'")
    public List<UniversityMajorSummary> getAll() {
        List<UniversityMajor> majors = universityMajorRepository.findAll();
        return majors.stream()
                .map(m -> new UniversityMajorSummary(m.getId(), m.getName()))
                .toList();
    }

}
