package com.iam.serviceacquisition.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.TalentLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentTechnicalProfilesDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("talent_id")
    @ApiModelProperty(required = true)
    private Long talentId;

    @JsonProperty("role")
    private String role;

    @JsonProperty("default_role_id")
    @ApiModelProperty(required = true)
    private Long defaultRoleId;

    @JsonProperty("level")
    @ApiModelProperty(required = true)
    private TalentLevel level;

    @JsonProperty("current_profile")
    @ApiModelProperty(required = true)
    private boolean currentProfile;

}
