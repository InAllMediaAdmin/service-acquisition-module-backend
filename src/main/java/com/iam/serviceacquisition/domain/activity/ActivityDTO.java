package com.iam.serviceacquisition.domain.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.dto.TeamPositionSlotDTO;
import com.iam.serviceacquisition.domain.teamrequest.dto.TeamRequestDTO;
import com.iam.user.account.common.model.UserDTO;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class ActivityDTO {

    private Long id;

    @JsonProperty("activity_type")
    private ActivityType type;

    @JsonProperty("talent_id")
    private TalentDTO talent;

    @JsonProperty("team_request_id")
    private TeamRequestDTO teamRequest;

    @JsonProperty("team_position_slot_id")
    private TeamPositionSlotDTO teamPositionSlot;

    @JsonProperty("author")
    private UserDTO author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @JsonProperty("created_at")
    private Instant created;
}
