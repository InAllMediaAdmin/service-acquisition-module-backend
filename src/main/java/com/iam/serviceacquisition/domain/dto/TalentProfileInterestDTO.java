package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentProfileInterestDTO {

    @JsonProperty("talent_id")
    @NotNull
    @ApiModelProperty(required = true)
    private Long talentId;

    @JsonProperty("profile_description")
    @Size(max = 400, message = "description field must be limited: Max 100 characters")
    private String profileDescription;

    @JsonProperty("interest_description")
    @Size(max = 400, message = "description field must be limited: Max 100 characters")
    private String interestDescription;

}
