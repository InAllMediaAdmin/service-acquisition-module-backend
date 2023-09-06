package com.iam.serviceacquisition.domain.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class EnglishLevelOpportunityInfoDTO {

    private String averageLevel;
    private List<Long> talents;

}
