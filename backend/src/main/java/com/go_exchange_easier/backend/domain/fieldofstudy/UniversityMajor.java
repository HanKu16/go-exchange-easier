package com.go_exchange_easier.backend.domain.fieldofstudy;

import com.go_exchange_easier.backend.domain.exchange.Exchange;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "university_majors")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UniversityMajor {

    @Id
    @Column(name = "university_major_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "universityMajor")
    private List<Exchange> exchanges = new ArrayList<>();

}
