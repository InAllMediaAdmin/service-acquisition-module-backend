package com.iam.serviceacquisition.highlights.domain.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class HighlightExperienceAverageDTO {

    @JsonProperty(value = "average")
    private final int average;
}
