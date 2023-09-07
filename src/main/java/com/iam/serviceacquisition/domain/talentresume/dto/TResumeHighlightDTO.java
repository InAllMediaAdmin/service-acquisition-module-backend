package com.iam.serviceacquisition.domain.talentresume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TResumeHighlightDTO {

    private Long id;

    @JsonProperty("strategy_highlight")
    private TResumeHighlightStrategyDTO highlightStrategy;
}
