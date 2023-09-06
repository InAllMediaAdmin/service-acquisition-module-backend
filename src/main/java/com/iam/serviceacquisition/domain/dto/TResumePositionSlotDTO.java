package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TResumePositionSlotDTO {

    @JsonProperty(value = "team_position_slot_id")
    @NotNull
    @ApiModelProperty(required = true)
    private Long id;

    @JsonProperty(value = "talent")
    private TalentDTO talent;
}
