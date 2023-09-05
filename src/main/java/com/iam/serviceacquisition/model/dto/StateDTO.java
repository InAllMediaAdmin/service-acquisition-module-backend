package com.iam.serviceacquisition.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class StateDTO {

    private Integer id;

    @JsonProperty(value = "description")
    private final String description;
}
