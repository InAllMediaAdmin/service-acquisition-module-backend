package com.iam.serviceacquisition.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchStrategyDTO {

    @JsonProperty("value")
    private List<Long> id;

    @JsonProperty("must")
    private Boolean must;
}
