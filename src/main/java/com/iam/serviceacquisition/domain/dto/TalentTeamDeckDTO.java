package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.Avatar;
import com.iam.user.account.common.model.TalentRoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentTeamDeckDTO {

    @ApiModelProperty(hidden = true)
    private Long id;



    @JsonProperty("avatar")
    private Avatar avatar;

    @JsonProperty("name")
    @ApiModelProperty(required = true)
    private String name;

    @JsonProperty("last_name")
    @ApiModelProperty(required = true)
    private String lastName;

    @JsonProperty("printable_name")
    private String printableName;

    private double experience;

    private List<TalentRoleDTO> roles;

}
