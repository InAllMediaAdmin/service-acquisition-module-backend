package com.iam.serviceacquisition.domain.teamrequest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.highlights.domain.HighLightDTO;
import com.iam.serviceacquisition.domain.activity.ActivityDTO;
import com.iam.serviceacquisition.domain.dto.ModalProposalStatusDTO;
import com.iam.serviceacquisition.domain.dto.TeamDTO;
import com.iam.serviceacquisition.domain.dto.TeamProposalDTO;
import com.iam.serviceacquisition.domain.dto.TeamRequestStatusDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TResumeDTO;
import com.iam.user.account.common.model.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class TeamRequestDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonProperty("team_id")
    private TeamDTO team;

    @JsonProperty("lead_name")
    private String leadName;

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("lead_project_name")
    private String leadProjectName;

    @JsonProperty("due_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dueDate;

    @JsonProperty("requested_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate requestedDate;

    @JsonProperty("talent_agent")
    private UserDTO talentAgentDTO;

    @JsonProperty("client_partner")
    private UserDTO clientPartnerDTO;

    @JsonProperty("status")
    private TeamRequestStatusDTO status;

    @JsonProperty("modal_proposal_status")
    private ModalProposalStatusDTO modalProposalStatus;

    @JsonProperty("resumes")
    private List<TResumeDTO> resumes;

    @JsonProperty("additional_information")
    @Size(max = 1500, message = "Additional Information field must be limited to: Max 1500 characters")
    private String additionalInformation;

    @JsonProperty("short_client_need_description")
    @Size(max = 60, message = "Short description field must be limited to: Max 60 characters")
    private String shortClientNeedDescription;

    @JsonProperty("long_client_need_description")
    @Size(max = 600, message = "Long description field must be limited to: Max 600 characters")
    private String longClientNeedDescription;

    @JsonProperty("highlights")
    @ApiModelProperty(hidden = true)
    private HighLightDTO highlights;

    @JsonProperty("activities")
    private List<ActivityDTO> activities;

    @JsonProperty("team_proposal")
    private TeamProposalDTO proposal;

    @JsonProperty("customer_lead_id")
    private Long customerLeadId;

    @JsonProperty("share_count")
    private Integer shareCount;

}
