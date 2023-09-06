package com.iam.serviceacquisition.domain;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Strategy {

    @Id
    @GeneratedValue
    private Long id;

    private String description;
}
