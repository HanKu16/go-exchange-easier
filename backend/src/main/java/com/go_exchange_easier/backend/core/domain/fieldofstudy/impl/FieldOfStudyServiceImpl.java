package com.go_exchange_easier.backend.core.domain.fieldofstudy.impl;

import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudy;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudyRepository;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudyService;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

    private final FieldOfStudyRepository fieldOfStudyRepository;

    @Override
    @Cacheable(value="fields_of_study", key="'all'")
    public List<FieldOfStudySummary> getAll() {
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyRepository.findAll();
        return fieldsOfStudy.stream()
                .map(f -> new FieldOfStudySummary(f.getId(), f.getName()))
                .toList();
    }

}
