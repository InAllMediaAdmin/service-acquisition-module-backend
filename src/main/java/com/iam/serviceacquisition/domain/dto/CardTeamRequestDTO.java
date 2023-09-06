package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardTeamRequestDTO {

    List<CardTeamRequestRolesDTO> roles;

    @JsonProperty("team_size")
    Integer teamSize;

    @JsonProperty("team_request_id")
    Long teamRequestId;

    String industry;

    @JsonProperty("blended_hourly_rate")
    private String blendedHourlyRate;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_request_status")
    private TeamRequestStatus teamRequestStatus;

    @JsonProperty("due_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dueDate;

    @JsonProperty("requested_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate requestedDate;

    @JsonProperty("share_count")
    private Integer shareCount;
}
