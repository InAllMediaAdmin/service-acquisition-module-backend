package com.iam.serviceacquisition.domain.talentresume;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@EqualsAndHashCode
public class TalentDegreeCertification {

    @Id
    @GeneratedValue
    private Long id;

    private String description;
}
