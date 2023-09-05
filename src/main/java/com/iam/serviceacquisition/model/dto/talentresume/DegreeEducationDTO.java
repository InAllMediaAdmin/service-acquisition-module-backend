package com.iam.serviceacquisition.model.dto.talentresume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
public class DegreeEducationDTO {
    private long id;

    @JsonProperty(value = "description")
    private final String description;
}
