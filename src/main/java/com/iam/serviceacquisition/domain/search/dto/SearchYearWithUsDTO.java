package com.iam.serviceacquisition.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchYearWithUsDTO {

    @JsonProperty("value")
    private Integer years;

    @JsonProperty("must")
    private Boolean must;
}
