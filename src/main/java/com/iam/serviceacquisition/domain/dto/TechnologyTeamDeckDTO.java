package com.iam.serviceacquisition.domain.dto;

import com.iam.user.account.common.model.TalentRoleDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
@EqualsAndHashCode
public class TechnologyTeamDeckDTO {

    private long id;

    @EqualsAndHashCode.Exclude
    private String description;

    @EqualsAndHashCode.Exclude
    private List<TalentRoleDTO> roles;

    @EqualsAndHashCode.Exclude
    private List<TalentTeamDeckDTO> talents;

    @EqualsAndHashCode.Exclude
    private Double yearsExperience;
}
