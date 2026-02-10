package com.go_exchange_easier.backend.domain.location.country;

import com.go_exchange_easier.backend.domain.location.city.City;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Country {

    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Short id;

    @Column(name = "english_name")
    private String englishName;

    @OneToMany(mappedBy = "country")
    private Set<City> cities = new HashSet<>();

}
