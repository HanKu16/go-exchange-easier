package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "universities")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class University {

    @Id
    @Column(name = "university_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Short id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "link_to_website")
    private String linkToWebsite;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "university")
    private Set<UniversityReview> reviews = new HashSet<>();

    @OneToMany(mappedBy = "university")
    private Set<Exchange> exchanges = new HashSet<>();

    @OneToMany(mappedBy = "homeUniversity")
    private Set<User> homeStudents = new HashSet<>();

}
