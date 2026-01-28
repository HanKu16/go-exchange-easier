package com.go_exchange_easier.backend.domain.university;

import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.university.dto.UniversityFilters;
import com.go_exchange_easier.backend.domain.university.dto.UniversityProfile;
import com.go_exchange_easier.backend.domain.university.specification.UniversitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    @Transactional(readOnly = true)
    public UniversityProfile getProfile(
            int universityId, int currentUserId) {
        List<Object[]> rows = universityRepository.findProfileById(
                universityId, currentUserId);
        if (rows.isEmpty()) {
            throw new UniversityNotFoundException("University of id " +
                    universityId + " was not found.");
        }
        Object[] row = rows.getFirst();
        Short id = (Short) row[0];
        String originalName = (String) row[1];
        String englishName = (String) row[2];
        String linkToWebsite = (String) row[3];
        String cityName = (String) row[4];
        String countryName = (String) row[5];
        Boolean isFollowed = (Boolean) row[6];
        return new UniversityProfile(id,
                originalName, englishName, linkToWebsite,
                cityName, countryName, isFollowed);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UniversityDetails> getPage(UniversityFilters filters, Pageable pageable) {
        Specification<University> specification = UniversitySpecification.fromFilter(filters);
        Page<University> universities = universityRepository
                .findAll(specification, pageable);
        return universities.map(UniversityDetails::fromEntity);
    }

}
