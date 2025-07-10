package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "english_name")
    private String englishName;

    @OneToMany(mappedBy = "countryOfOrigin")
    private Set<User> nationals = new HashSet<>();

    @OneToMany(mappedBy = "country")
    private Set<City> cities = new HashSet<>();

}
