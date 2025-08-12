package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reaction_types")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReactionType {

    @Id
    @Column(name = "reaction_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Short id;

    @Column(name = "name")
    private String name;

}
