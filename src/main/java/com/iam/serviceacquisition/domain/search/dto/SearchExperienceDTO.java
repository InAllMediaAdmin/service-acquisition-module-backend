package com.iam.serviceacquisition.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchExperienceDTO {

    @JsonProperty("value")
    private Integer experience;

    @JsonProperty("must")
    private Boolean must;
}
