package com.go_exchange_easier.backend.core.domain.moderation;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "report_resolutions", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReportResolution {

    @Id
    @Column(name = "report_resolution_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "report_id")
    private UUID reportId;

}
