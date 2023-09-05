package com.iam.serviceacquisition.model.dto.talentresume;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.model.dto.StrategyDTO;
import com.iam.serviceacquisition.model.dto.TalentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TSoftSkillDTO {

    private Long id;

    @JsonProperty("talent_id")
    @NotNull
    @ApiModelProperty(required = true)
    private TalentDTO talent;

    @JsonProperty("strategy")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private StrategyDTO strategy;

    @JsonProperty("description")
    @Size(min= 100, max = 300, message = "description field must be limited: Min 100 - Max 300 characters")
    private String description;

    @JsonProperty("name_person")
    private String namePerson;

    @JsonProperty("role_person")
    private String rolePerson;

    @JsonProperty("talent_experience_id")
    private Long talentExperienceId;
}
