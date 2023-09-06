package com.iam.serviceacquisition.domain.talentresume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.IndustryDTO;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TIndustryExpertiseDTO {

    private Long id;

    @JsonProperty("talent_id")
    @NotNull
    @ApiModelProperty(required = true)
    private final TalentDTO talent;

    @JsonProperty(value = "industry")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private final IndustryDTO industry;

    @JsonProperty("description")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String description;

    private final int years;
}
