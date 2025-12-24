package com.go_exchange_easier.backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.go_exchange_easier.backend.dto.details.ReactionDetails;
import com.go_exchange_easier.backend.dto.details.UniversityReviewDetails;
import com.go_exchange_easier.backend.dto.summary.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.dto.summary.UniversitySummary;
import com.go_exchange_easier.backend.dto.summary.UserSummary;
import com.go_exchange_easier.backend.dto.universityReview.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import com.go_exchange_easier.backend.dto.universityReview.UniversityReviewReactionDetail;
import com.go_exchange_easier.backend.exception.JsonParsingException;
import com.go_exchange_easier.backend.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UniversityReviewReactionNotFoundException;
import com.go_exchange_easier.backend.model.ReactionType;
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
    private final ReactionTypeRepository reactionTypeRepository;
    private final ResourceOwnershipChecker resourceOwnershipChecker;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;

    @Override
    public List<UniversityReviewDetails> getByAuthorId(
            int authorId, int currentUserId) {
        List<Object[]> rows = universityReviewRepository
                .findByAuthorId(authorId, currentUserId);
        List<UniversityReviewDetails> reviews = new ArrayList<>();
        for (Object[] row : rows) {
            Integer id = (Integer) row[0];
            Integer authorIdRow = (Integer) row[1];
            String authorNick = (String) row[2];
            Short universityId = (Short) row[3];
            String universityEnglishName = (String) row[4];
            String universityNativeName = (String) row[5];
            Short starRating = (Short) row[6];
            String textContent = (String) row[7];
            Instant createdAt = (Instant) row[8];
            String reactionsJson = (String) row[9];
            List<ReactionDetails> reactions = parseReactionsJson(reactionsJson);
            reviews.add(new UniversityReviewDetails(id, new UserSummary(authorIdRow, authorNick),
                    new UniversitySummary(universityId, universityEnglishName, universityNativeName),
                    starRating, textContent, createdAt, reactions));
        }
        return reviews;
    }

    @Override
    public List<UniversityReviewDetails> getByUniversityId(
            int universityId, int currentUserId, int page, int size) {
        int limit = size;
        int offset = page * size;
        List<Object[]> rows = universityReviewRepository
                .findByUniversityId(universityId, currentUserId, limit, offset);
        List<UniversityReviewDetails> reviews = new ArrayList<>();
        for (Object[] row : rows) {
            Integer id = (Integer) row[0];
            Integer authorId = (Integer) row[1];
            String authorNick = (String) row[2];
            Short universityIdFromDb = (Short) row[3];
            String universityEnglishName = (String) row[4];
            String universityNativeName = (String) row[5];
            Short starRating = (Short) row[6];
            String textContent = (String) row[7];
            Instant createdAt = (Instant) row[8];
            String reactionsJson = (String) row[9];
            List<ReactionDetails> reactions = parseReactionsJson(reactionsJson);
            reviews.add(new UniversityReviewDetails(id,
                    new UserSummary(authorId, authorNick),
                    new UniversitySummary(universityIdFromDb, universityNativeName, universityEnglishName),
                    starRating, textContent, createdAt, reactions));
        }
        return reviews;
    }

    @Override
    @Transactional
    public UniversityReviewDetails create(int userId, CreateUniversityReviewRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ReferencedResourceNotFoundException(
                        "User of id " + userId + " was not found.")
        );
        University university = universityRepository
                .findById(request.universityId())
                .orElseThrow(() -> new ReferencedResourceNotFoundException(
                        "University of id " + request.universityId() +
                                " was not found."));
        UniversityReview review = buildUniversityReview(request, user, university);
        UniversityReview savedReview = universityReviewRepository.save(review);
        List<UniversityReviewReactionDetail> reactionDetails =
                reactionCountService.createCounts(savedReview);
        List<ReactionType> reactionTypes = reactionTypeRepository.findAll();
        List<ReactionDetails> reactions =  reactionTypes.stream()
                .map(t -> new ReactionDetails(t.getId(), t.getName(), (short) 0, false))
                .toList();
        return new UniversityReviewDetails(
                savedReview.getId(), new UserSummary(user.getId(), user.getNick()),
                new UniversitySummary(university.getId(), university.getOriginalName(),
                        university.getEnglishName()),
                review.getStarRating(), review.getTextContent(),
                review.getCreatedAt().toInstant(), reactions);
//        return new CreateUniversityReviewResponse(
//                savedReview.getId(),
//                new CreateUniversityReviewResponse.AuthorDto(userId, user.getNick()),
//                new CreateUniversityReviewResponse.UniversityDto(
//                        university.getId(), university.getEnglishName(),
//                        university.getOriginalName()),
//                review.getStarRating(),
//                review.getTextContent(),
//                review.getCreatedAt(),
//                reactionTypes.stream().map(t -> new CreateUniversityReviewResponse
//                        .ReactionDetailDto(t.getId(), t.getName(), (short) 0, false))
//                        .toList()
//        );
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

    @Override
    public UniversityReviewCountSummary countByUniversityId(int universityId) {
        int count = universityReviewRepository.countReviewsByUniversityId(universityId);
        return new UniversityReviewCountSummary((short) universityId, count);
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

    private List<GetUniversityReviewResponse.ReactionDetailDto> parseReactionsJsonOld(
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

    private List<ReactionDetails> parseReactionsJson(
            String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference
                    <List<ReactionDetails>>() {});
        } catch (Exception e) {
            throw new JsonParsingException("Parsing " + json + " to " +
                    "List<ReactionDetails> failed.");
        }
    }

}
