package com.iam.serviceacquisition.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalentProfit {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal value;

    private LocalDateTime date;
}
