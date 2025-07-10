package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "university_majors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityMajor {

    @Id
    @Column(name = "university_major_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "universityMajor")
    private List<Exchange> exchanges = new ArrayList<>();

}
