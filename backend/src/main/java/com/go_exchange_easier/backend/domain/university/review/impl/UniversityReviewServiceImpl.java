package com.go_exchange_easier.backend.domain.university.review.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.domain.reaction.ReactionType;
import com.go_exchange_easier.backend.domain.reaction.ReactionTypeRepository;
import com.go_exchange_easier.backend.domain.university.*;
import com.go_exchange_easier.backend.domain.reaction.ReactionDetails;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewDetails;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewCountSummary;
import com.go_exchange_easier.backend.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewReactionCountService;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewRepository;
import com.go_exchange_easier.backend.domain.university.review.UniversityReviewService;
import com.go_exchange_easier.backend.domain.university.review.entity.UniversityReview;
import com.go_exchange_easier.backend.domain.user.UserRepository;
import com.go_exchange_easier.backend.domain.user.dto.UserWithAvatarSummary;
import com.go_exchange_easier.backend.domain.university.review.dto.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.domain.university.review.dto.UniversityReviewReactionDetails;
import com.go_exchange_easier.backend.common.exception.JsonParsingException;
import com.go_exchange_easier.backend.common.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.user.avatar.AvatarService;
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
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;
    private final AvatarService avatarService;

    @Override
    @Transactional(readOnly = true)
    public List<UniversityReviewDetails> getByAuthorId(
            int authorId, int currentUserId) {
        List<Object[]> rows = universityReviewRepository
                .findByAuthorId(authorId, currentUserId);
        List<UniversityReviewDetails> reviews = new ArrayList<>();
        String avatarUrl = null;
        boolean wasAttemptToSetAvatar = false;
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
            if (!wasAttemptToSetAvatar) {
                String avatarKey = row[10] != null ? (String) row[10] : null;
                if (avatarKey != null) {
                    avatarUrl = avatarService.getUrl(avatarKey).thumbnail();
                }
                wasAttemptToSetAvatar = true;
            }
            reviews.add(new UniversityReviewDetails(id, new UserWithAvatarSummary(
                    authorIdRow, authorNick, avatarUrl),
                    new UniversitySummary(universityId, universityEnglishName,
                            universityNativeName), starRating, textContent,
                    createdAt, reactions));
        }
        return reviews;
    }

    @Override
    @Transactional(readOnly = true)
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
            String avatarKey = row[10] != null ? (String) row[10] : null;
            String avatarUrl = null;
            if (avatarKey != null) {
                avatarUrl = avatarService.getUrl(avatarKey).thumbnail();
            }
            List<ReactionDetails> reactions = parseReactionsJson(reactionsJson);
            reviews.add(new UniversityReviewDetails(id,
                    new UserWithAvatarSummary(authorId, authorNick, avatarUrl),
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
        List<UniversityReviewReactionDetails> reactionDetails =
                reactionCountService.createCounts(savedReview);
        List<ReactionType> reactionTypes = reactionTypeRepository.findAll();
        List<ReactionDetails> reactions =  reactionTypes.stream()
                .map(t -> new ReactionDetails(t.getId(), t.getName(), (short) 0, false))
                .toList();
        String avatarKey = user.getAvatarKey();
        String avatarUrl = null;
        if (avatarKey != null) {
            avatarUrl = avatarService.getUrl(avatarKey).thumbnail();
        }
        return new UniversityReviewDetails(
                savedReview.getId(), new UserWithAvatarSummary(user.getId(), user.getNick(), avatarUrl),
                new UniversitySummary(university.getId(), university.getOriginalName(),
                        university.getEnglishName()),
                review.getStarRating(), review.getTextContent(),
                review.getCreatedAt().toInstant(), reactions);
    }

    @Override
    @Transactional
    public void delete(int reviewId, int userId) {
        UniversityReview review = universityReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "University " + "review of id " + reviewId + " was not found."));
        if (!review.getAuthor().getId().equals(userId)) {
            throw new NotOwnerOfResourceException("Authenticated user is not " +
                    "entitled to delete university review of id " + reviewId + ".");
        }
        reactionCountService.deleteCounts(review.getReactionCounts().stream().toList());
        universityReviewRepository.delete(review);
    }

    @Override
    @Transactional(readOnly = true)
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
