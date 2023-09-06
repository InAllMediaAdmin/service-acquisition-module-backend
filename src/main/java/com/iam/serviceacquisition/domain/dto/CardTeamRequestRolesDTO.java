package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardTeamRequestRolesDTO {

    @JsonProperty("role_amount")
    private int roleAmount;

    private String role;

    @JsonProperty("is_tech_lead")
    private boolean isTechLead;

    @JsonProperty("is_technical_rol")
    private boolean isTechnicalRol;

    @JsonProperty("main_technologies")
    private List<TechnologyDTO> mainTechnologies;


}
