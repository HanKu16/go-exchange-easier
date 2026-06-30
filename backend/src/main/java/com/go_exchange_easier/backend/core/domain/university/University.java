package com.go_exchange_easier.backend.core.domain.university;

import com.go_exchange_easier.backend.core.domain.location.city.City;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "universities", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SQLRestriction("deleted_at IS NULL")
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

}
