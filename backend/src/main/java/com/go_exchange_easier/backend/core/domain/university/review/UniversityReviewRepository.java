package com.go_exchange_easier.backend.core.domain.university.review;

import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UniversityReviewRepository extends
        JpaRepository<UniversityReview, Integer> {

    @Query(value = """
        SELECT
            ur.university_review_id,
            us.user_id,
            us.nick,
            un.university_id,
            un.english_name,
            un.original_name,
            ur.star_rating,
            ur.text_content,
            ur.created_at,
            r.reactions,
            us.avatar_key,
            co.country_id,
            co.english_name,
            co.flag_key,
            ci.city_id,
            ci.english_name
        FROM core.university_reviews ur
        LEFT JOIN core.users us ON us.user_id = ur.author_id
        LEFT JOIN core.universities un ON un.university_id = ur.university_id
        LEFT JOIN core.cities ci ON ci.city_id = un.city_id
        LEFT JOIN core.countries co ON co.country_id = ci.country_id
        LEFT JOIN LATERAL (
            SELECT
                COALESCE(
                    json_agg(
                        json_build_object(
                            'type', rc.reaction_type,
                            'count', rc.count,
                            'isSet', (urr.author_id IS NOT NULL)
                        )
                    ),
                    '[]'::json
                ) AS reactions
            FROM core.university_reviews_reaction_counts rc
            LEFT JOIN core.university_review_reactions urr ON
                urr.university_review_id = rc.university_review_id
                AND urr.reaction_type = rc.reaction_type
                AND urr.author_id = :currentUserId
            WHERE rc.university_review_id = ur.university_review_id
            ) r ON true
        WHERE us.user_id = :authorId AND us.deleted_at IS NULL
        AND un.deleted_at IS NULL AND ur.deleted_at IS NULL
        ORDER BY ur.created_at DESC
    """, nativeQuery = true)
    List<Object[]> findByAuthorId(@Param("authorId") int authorId,
            @Param("currentUserId") int currentUserId);

    @Query(value = """
        SELECT
            ur.university_review_id,
            us.user_id,
            us.nick,
            un.university_id,
            un.english_name,
            un.original_name,
            ur.star_rating,
            ur.text_content,
            ur.created_at,
            r.reactions,
            us.avatar_key,
            co.country_id,
            co.english_name,
            co.flag_key,
            ci.city_id,
            ci.english_name
        FROM core.university_reviews ur
        LEFT JOIN core.users us ON us.user_id = ur.author_id
        LEFT JOIN core.universities un ON un.university_id = ur.university_id
        LEFT JOIN core.cities ci ON ci.city_id = un.city_id
        LEFT JOIN core.countries co ON co.country_id = ci.country_id
        LEFT JOIN LATERAL (
            SELECT
                COALESCE(
                    json_agg(
                        json_build_object(
                            'type', rc.reaction_type,
                            'count', rc.count,
                            'isSet', (urr.author_id IS NOT NULL)
                        )
                    ),
                    '[]'::json
                ) AS reactions
            FROM core.university_reviews_reaction_counts rc
        LEFT JOIN core.university_review_reactions urr ON
        urr.university_review_id = rc.university_review_id AND
        urr.reaction_type = rc.reaction_type AND
        urr.author_id = :currentUserId
        WHERE rc.university_review_id = ur.university_review_id
        ) r ON true
        WHERE un.university_id = :universityId
        AND un.deleted_at IS NULL
        AND us.deleted_at IS NULL
        AND ur.deleted_at IS NULL
        ORDER BY ur.created_at DESC
        LIMIT :limit
        OFFSET :offset;
        """, nativeQuery = true)
    List<Object[]> findByUniversityId(@Param("universityId") int universityId,
            @Param("currentUserId") int currentUserId, @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(value = """
            SELECT
            	COUNT(ur.university_review_id)
            FROM core.university_reviews ur
            JOIN core.users us ON us.user_id = ur.author_id
            JOIN core.universities un ON un.university_id = ur.university_id
            WHERE ur.university_id = :universityId AND ur.deleted_at IS NULL AND
            	us.deleted_at IS NULL AND un.deleted_at IS NULL
            """, nativeQuery = true)
    int countReviewsByUniversityId(@Param("universityId") int universityId);

}


