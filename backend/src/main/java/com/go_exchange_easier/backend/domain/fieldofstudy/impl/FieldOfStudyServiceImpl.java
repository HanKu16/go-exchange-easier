package com.go_exchange_easier.backend.domain.fieldofstudy.impl;

import com.go_exchange_easier.backend.domain.fieldofstudy.FieldOfStudy;
import com.go_exchange_easier.backend.domain.fieldofstudy.FieldOfStudyRepository;
import com.go_exchange_easier.backend.domain.fieldofstudy.FieldOfStudyService;
import com.go_exchange_easier.backend.domain.fieldofstudy.FieldOfStudySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="university_majors", key="'all'")
    public List<FieldOfStudySummary> getAll() {
        List<FieldOfStudy> majors = fieldOfStudyRepository.findAll();
        return majors.stream()
                .map(m -> new FieldOfStudySummary(m.getId(), m.getName()))
                .toList();
    }

}
