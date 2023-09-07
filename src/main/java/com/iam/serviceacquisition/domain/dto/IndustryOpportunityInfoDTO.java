package com.iam.serviceacquisition.domain.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class IndustryOpportunityInfoDTO {

    private double sumOfExperience;
    private double averageExperience;
    private List<Long> talents;
    private List<String> companies;
    private long industryId;

}
