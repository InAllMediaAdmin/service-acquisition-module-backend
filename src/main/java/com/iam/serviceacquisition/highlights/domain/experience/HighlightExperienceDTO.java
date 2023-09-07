package com.iam.serviceacquisition.highlights.domain.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.highlights.domain.HighLightData;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class HighlightExperienceDTO implements HighLightData {

    @JsonProperty(value = "leaders")
    private final HighlightExperienceAverageDTO leaderAverage;

    @JsonProperty(value = "team")
    private final HighlightExperienceAverageDTO teamAverage;

    @JsonProperty(value = "must")
    private final boolean must;
}
