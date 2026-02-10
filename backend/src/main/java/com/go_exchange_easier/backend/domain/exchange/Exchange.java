package com.go_exchange_easier.backend.domain.exchange;

import com.go_exchange_easier.backend.domain.user.User;
import com.go_exchange_easier.backend.domain.university.University;
import com.go_exchange_easier.backend.domain.fieldofstudy.FieldOfStudy;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "exchanges", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Exchange {

    @Id
    @Column(name = "exchange_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne
    @JoinColumn(name = "field_of_study_id")
    private FieldOfStudy fieldOfStudy;

}
