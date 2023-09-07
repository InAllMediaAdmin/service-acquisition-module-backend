package com.iam.serviceacquisition.highlights.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
public class HighLightDTO {

    @JsonProperty(value = "english")
    private HighLightData highLightEnglishLevelDTO;

    @JsonProperty(value = "strategy")
    private HighLightData highlightStrategyDTO;

    @JsonProperty(value = "industry")
    private HighLightData highlightIndustryDTO;

    @JsonProperty(value = "experience")
    private HighLightData highlightExperienceDTO;
}
