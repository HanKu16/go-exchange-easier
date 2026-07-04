package com.go_exchange_easier.backend.core.domain.user.description;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "user_descriptions", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDescription implements Persistable<UUID> {

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        isNew = false;
    }

}
