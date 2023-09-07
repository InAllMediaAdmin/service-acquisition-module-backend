package com.iam.serviceacquisition.domain.teamrequest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.CommentDTO;
import com.iam.serviceacquisition.domain.dto.IndustryDTO;
import com.iam.serviceacquisition.domain.dto.TechnologyDTO;
import com.iam.serviceacquisition.domain.dto.TimeZoneDTO;
import com.iam.user.account.common.model.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Data
public class CPRequestDTO {

    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("client_partner_id")
    private Long clientPartner;

    @JsonProperty("client_partner")
    @ApiModelProperty(hidden = true)
    private UserDTO userDTO;

    @JsonProperty("industry")
    private IndustryDTO industry;

    @JsonProperty("status")
    private CPRequestStatusDTO statusDTO;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty(value = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @ApiModelProperty(hidden = true)
    private Instant updatedAt;

    @JsonProperty("technologies")
    private List<TechnologyDTO> technologies = newArrayList();

    @JsonProperty("min_team_composition")
    private Integer minTeamComposition;

    @JsonProperty("max_team_composition")
    private Integer maxTeamComposition;

    @JsonProperty("time_zone_id")
    private TimeZoneDTO timeZone;

    @JsonProperty("blended_hourly_rate")
    private String blendedHourlyRate;

    @JsonProperty("customer_lead_id")
    private Long customerLeadId;

    @JsonProperty("lead_project_name")
    private String leadProjectName;

    @JsonProperty("stake_holder_name")
    private String stakeHolderName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("customer_lead_location")
    private String customerLeadLocation;

    @JsonProperty("pre_sales_user_id")
    private Long preSalesUserId;

    @JsonProperty("pre_sales_user")
    @ApiModelProperty(hidden = true)
    private UserDTO preSalesUser;

    @JsonProperty("last_comment")
    private CommentDTO lastComment;

}
