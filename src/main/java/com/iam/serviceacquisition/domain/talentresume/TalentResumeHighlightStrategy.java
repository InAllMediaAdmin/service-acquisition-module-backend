package com.iam.serviceacquisition.domain.talentresume;

import com.iam.serviceacquisition.domain.Strategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class TalentResumeHighlightStrategy {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany
    private List<Strategy> strategies;
}
