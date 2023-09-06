package com.iam.serviceacquisition.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchEnglishLevelDTO {

    @JsonProperty("value")
    private Integer englishLevel;

    @JsonProperty("must")
    private Boolean must;
}
