package com.go_exchange_easier.backend.core.domain.university.impl;

import com.go_exchange_easier.backend.core.domain.follow.university.UniversityFollowService;
import com.go_exchange_easier.backend.core.domain.university.*;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityPublicProfile;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityFilters;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityServiceImpl implements UniversityService {

    private final UniversityPublicProfileProvider publicProfileProvider;
    private final UniversityFollowService universityFollowService;
    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;

    @Override
    public UniversityProfile getProfile(
            short universityId, int currentUserId) {
        UniversityPublicProfile publicProfile = publicProfileProvider
                .getProfile(universityId);
        boolean isFollowed = universityFollowService.doesFollowExist(
                universityId, currentUserId);
        return new UniversityProfile(
                publicProfile.id(),
                publicProfile.nativeName(),
                publicProfile.englishName(),
                publicProfile.linkToWebsite(),
                publicProfile.city(),
                isFollowed
        );
    }

    @Override
    public Page<UniversityDetails> getPage(
            UniversityFilters filters, Pageable pageable) {
        Specification<University> specification =
                UniversitySpecification.fromFilter(filters);
        Page<University> universities = universityRepository
                .findAll(specification, pageable);
        return universities.map(universityMapper::toDetails);
    }

}
