package com.iam.serviceacquisition.domain;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter @Setter @Builder @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Industry {

    @Id
    @GeneratedValue
    private Long id;

    private String description;
}
