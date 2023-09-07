package com.iam.serviceacquisition.domain.talentresume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class TalentDegreeEducation {

    @Id
    @GeneratedValue
    private Long id;

    String description;
}
