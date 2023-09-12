package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class SimpleDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;

}
