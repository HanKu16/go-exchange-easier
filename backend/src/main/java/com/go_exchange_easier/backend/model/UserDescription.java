package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_descriptions")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDescription {

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

}
