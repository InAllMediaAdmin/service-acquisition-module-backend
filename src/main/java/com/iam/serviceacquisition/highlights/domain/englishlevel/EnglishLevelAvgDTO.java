package com.iam.serviceacquisition.highlights.domain.englishlevel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class EnglishLevelAvgDTO {

    @JsonProperty(value = "avg")
    private final String englishLevel;

    @JsonProperty(value = "percent")
    private final int percent;
}
