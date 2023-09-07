package com.iam.serviceacquisition.highlights.domain.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.highlights.domain.HighLightData;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class HighlightStrategyDTO implements HighLightData {

    @JsonProperty("strategies")
    private final Map<Long, Integer> strategies;

    @JsonProperty(value = "must")
    private final boolean must;
}
