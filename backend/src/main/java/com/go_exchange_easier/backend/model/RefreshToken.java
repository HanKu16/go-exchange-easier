package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RefreshToken {

    @Id
    @Column(name="refresh_token_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name="hashed_token")
    private UUID hashedToken;

    @Column(name="created_at")
    private OffsetDateTime createdAt;

    @Column(name="expires_at")
    private OffsetDateTime expiresAt;

    @Column(name="is_revoked")
    private boolean isRevoked;

    @Column(name="device_id")
    private UUID deviceId;

    @Column(name="device_name")
    private String deviceName;

    @Column(name="ip_address")
    private String ipAddress;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}

