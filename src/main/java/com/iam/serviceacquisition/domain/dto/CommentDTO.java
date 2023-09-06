package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.teamrequest.dto.TeamRequestDTO;
import com.iam.user.account.common.model.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class CommentDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("body")
    @ApiModelProperty(required = true)
    private String body;

    @JsonProperty("attachment")
    private String attachment;

    @JsonProperty("type")
    @ApiModelProperty(required = true)
    private String activityType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO activityAuthor;

    @JsonProperty("talent")
    @ApiModelProperty(required = true)
    private TalentDTO activityTalent;

    @JsonProperty("team_position_slot")
    private TeamPositionSlotDTO activityTeamPositionSlot;

    @JsonProperty("team_request")
    private TeamRequestDTO activityTeamRequest;

    @JsonProperty("cp_request_id")
    private Long activityCPRequestId;

    @JsonProperty("read_by_tm")
    private boolean readByTM;

    @JsonProperty("read_by_cp")
    private boolean readByCP;

    @JsonProperty("created")
    @ApiModelProperty(required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant activityCreated;

}
