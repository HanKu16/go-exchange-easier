package com.go_exchange_easier.backend.domain.fieldofstudy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "university_majors")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FieldOfStudy {

    @Id
    @Column(name = "university_major_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Short id;

    @Column(name = "name")
    private String name;

}
