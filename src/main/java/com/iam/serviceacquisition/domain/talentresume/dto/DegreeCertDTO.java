package com.iam.serviceacquisition.domain.talentresume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
public class DegreeCertDTO {

    private long id;

    @JsonProperty(value = "description")
    private final String description;
}
