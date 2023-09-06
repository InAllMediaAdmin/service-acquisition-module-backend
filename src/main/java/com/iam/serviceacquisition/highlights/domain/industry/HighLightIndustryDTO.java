package com.iam.serviceacquisition.highlights.domain.industry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.highlights.domain.HighLightData;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class HighLightIndustryDTO implements HighLightData {
    @JsonProperty(value = "id")
    private final Long id;

    @JsonProperty(value = "percent")
    private final int percent;

    @JsonProperty(value = "must")
    private final boolean must;
}
