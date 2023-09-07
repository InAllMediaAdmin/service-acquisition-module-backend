package com.iam.serviceacquisition.domain.talentresume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.StrategyDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TResumeHighlightStrategyDTO {

    private Long id;

    @JsonProperty("strategies")
    private List<StrategyDTO> strategies;
}
