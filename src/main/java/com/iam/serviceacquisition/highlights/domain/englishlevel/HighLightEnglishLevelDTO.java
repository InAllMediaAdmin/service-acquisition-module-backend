package com.iam.serviceacquisition.highlights.domain.englishlevel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.highlights.domain.HighLightData;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class HighLightEnglishLevelDTO implements HighLightData {

    @JsonProperty(value = "leaders")
    private final EnglishLevelAvgDTO avgLeaders;

    @JsonProperty(value = "team")
    private final EnglishLevelAvgDTO avgTeam;

    @JsonProperty(value = "must")
    private final boolean must;
}
