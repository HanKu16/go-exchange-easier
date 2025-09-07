package com.go_exchange_easier.backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.go_exchange_easier.backend.dto.university.*;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import com.go_exchange_easier.backend.exception.JsonParsingException;
import com.go_exchange_easier.backend.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UniversityReviewReactionNotFoundException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.model.UniversityReview;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.repository.*;
import com.go_exchange_easier.backend.service.ResourceOwnershipChecker;
import com.go_exchange_easier.backend.service.UniversityReviewReactionCountService;
import com.go_exchange_easier.backend.service.UniversityReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityReviewServiceImpl implements UniversityReviewService {

    private final UniversityReviewReactionCountService reactionCountService;
    private final UniversityReviewRepository universityReviewRepository;
    private final ResourceOwnershipChecker resourceOwnershipChecker;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;

    @Override
    public List<GetUniversityReviewResponse> getByAuthorId(
            int authorId, int currentUserId) {
        List<Object[]> rows = universityReviewRepository
                .findByAuthorId(authorId, currentUserId);
        List<GetUniversityReviewResponse> reviews = new ArrayList<>();

        for (Object[] row : rows) {
            Integer id = (Integer) row[0];
            Integer authorIdRow = (Integer) row[1];
            String authorNick = (String) row[2];
            Short university = (Short) row[3];
            String universityEnglishName = (String) row[4];
            String universityNativeName = (String) row[5];
            Short starRating = (Short) row[6];
            String textContent = (String) row[7];
            Instant createdAt = (Instant) row[8];
            String reactionsJson = (String) row[9];

            List<GetUniversityReviewResponse.ReactionDetailDto> reactions =
                    parseReactionsJson(reactionsJson);
            reviews.add(new GetUniversityReviewResponse(id,
                    new GetUniversityReviewResponse.AuthorDto(authorIdRow, authorNick),
                    new GetUniversityReviewResponse.UniversityDto(
                            university, universityEnglishName, universityNativeName),
                    starRating, textContent, createdAt, reactions));
        }
        return reviews;
    }

    @Override
    @Transactional
    public CreateUniversityReviewResponse create(int userId,
            CreateUniversityReviewRequest request) {
        if (!universityRepository.existsById(request.universityId())) {
            throw new ReferencedResourceNotFoundException("University of id "
                    + request.universityId() + " was not found.");
        }
        User user = userRepository.getReferenceById(userId);
        University university = universityRepository
                .getReferenceById(request.universityId());
        UniversityReview review = buildUniversityReview(request, user, university);
        UniversityReview savedReview = universityReviewRepository.save(review);
        List<UniversityReviewReactionDetail> reactionDetails =
                reactionCountService.createCounts(savedReview);
        return new CreateUniversityReviewResponse(savedReview.getId(),
                savedReview.getCreatedAt());
    }

    @Override
    @Transactional
    public void delete(int reviewId) {
        UniversityReview review = universityReviewRepository.findById(reviewId)
                .orElseThrow(() -> new UniversityReviewReactionNotFoundException(
                        "University " + "review of id " + reviewId + " was not found."));
        if (!resourceOwnershipChecker.isOwner(review)) {
            throw new NotOwnerOfResourceException("Authenticated user is not " +
                    "entitled to delete university review of id " + reviewId + ".");
        }
        reactionCountService.deleteCounts(review.getReactionCounts().stream().toList());
        universityReviewRepository.delete(review);
    }

    private UniversityReview buildUniversityReview(
            CreateUniversityReviewRequest request,
            User user, University university) {
        UniversityReview review = new UniversityReview();
        review.setTextContent(request.text());
        review.setStarRating(request.starRating());
        review.setCreatedAt(OffsetDateTime.now());
        review.setAuthor(user);
        review.setUniversity(university);
        return review;
    }

    private List<GetUniversityReviewResponse.ReactionDetailDto> parseReactionsJson(
            String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference
                    <List<GetUniversityReviewResponse.ReactionDetailDto>>() {});
        } catch (Exception e) {
            throw new JsonParsingException("Parsing " + json + " to " +
                    "List<GetUniversityReviewResponse.ReactionDetailDto> failed.");
        }
    }

}
