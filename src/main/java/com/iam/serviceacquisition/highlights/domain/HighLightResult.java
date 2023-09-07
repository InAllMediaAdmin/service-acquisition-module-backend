package com.iam.serviceacquisition.highlights.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class HighLightResult {

    @JsonProperty("highlight_name")
    private final HighLightNames highLightName;

    @JsonProperty("highlight_data")
    private final HighLightData highLightData;
}
