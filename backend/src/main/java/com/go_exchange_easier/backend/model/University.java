package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "universities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class University {

    @Id
    @Column(name = "university_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "link_to_website")
    private String linkToWebsite;

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
