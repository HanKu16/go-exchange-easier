package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_descriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDescription {

    @Id
    @Column(name = "user_id")
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
