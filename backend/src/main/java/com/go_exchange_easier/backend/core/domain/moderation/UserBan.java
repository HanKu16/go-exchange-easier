package com.go_exchange_easier.backend.core.domain.moderation;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_bans", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserBan {

    @Id
    @Column(name = "user_ban_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "explanation_for_user")
    private String explanationForUser;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "banned_user_id")
    private int bannedUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_resolution_id")
    private ReportResolution reportResolution;

}
