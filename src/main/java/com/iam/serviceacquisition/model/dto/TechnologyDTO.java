package com.iam.serviceacquisition.model.dto;

import com.iam.user.account.common.model.TalentRoleDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
@EqualsAndHashCode
public class TechnologyDTO {

    private long id;

    @EqualsAndHashCode.Exclude
    private String description;

    @EqualsAndHashCode.Exclude
    private List<TalentRoleDTO> roles;
}
