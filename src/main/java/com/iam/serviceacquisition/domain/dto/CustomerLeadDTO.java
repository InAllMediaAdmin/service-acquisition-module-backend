package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import com.iam.serviceacquisition.domain.Technology;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class CustomerLeadDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty("lead_project_name")
    private String leadProjectName;

    @JsonProperty(value = "stakeholder")
    private StakeholderDTO stakeholder;

    @JsonProperty(value = "company")
    private CompanyDTO company;

    @JsonProperty(value = "industry", required = true)
    private IndustryDTO industry;

    @JsonProperty("location")
    private String location;

    @JsonProperty("time_zone")
    private TimeZoneDTO timeZone;

    @JsonProperty("additional_notes")
    private String additionalNotes;

    @JsonProperty(value = "client_partner")
    private Long clientPartner;

    @JsonProperty("status_last_team_request")
    private TeamRequestStatus statusLastTeamRequest;

    @JsonProperty(value = "main_technologies_last_team_request")
    private List<Technology> mainTechnologiesLastTeamRequest;

    @JsonProperty("blended_rate_last_team_request")
    private BigDecimal blendedRateLastTeamRequest;

    @JsonProperty("requested_date_last_team_request")
    private LocalDate requestedDateLastTeamRequest;

    @JsonProperty("members_size_last_team_request")
    private Integer membersSizeLastTeamRequest;

    @JsonProperty("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant created;

    @JsonProperty(value = "cp_request_id_list")
    private List<Long> cpRequestIdList;

}
