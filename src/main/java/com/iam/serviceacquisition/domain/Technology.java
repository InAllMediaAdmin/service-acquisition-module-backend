package com.iam.serviceacquisition.domain;


import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @EqualsAndHashCode.Exclude
    private String description;

    @ElementCollection
    @CollectionTable(name = "technology_roles", joinColumns = @JoinColumn(name = "technology_id"))
    @Column(name = "default_role_id")
    Set<Long> roles = new HashSet<>();

    public Technology(Long id) {
        this.id = id;
    }
}
