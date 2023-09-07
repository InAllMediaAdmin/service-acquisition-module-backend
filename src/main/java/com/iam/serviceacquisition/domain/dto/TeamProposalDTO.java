package com.iam.serviceacquisition.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import com.iam.serviceacquisition.domain.talentresume.dto.TResumeDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TSoftSkillDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeamProposalDTO {

    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @JsonProperty("created_at")
    private Instant created;

    @JsonProperty("team_name")
    @NotNull
    @ApiModelProperty(required = true)
    private String teamName;

    @JsonProperty("team_overview")
    @NotNull
    @ApiModelProperty(required = true)
    private String teamOverview;

    @JsonInclude(NON_NULL)
    @JsonProperty("blended_rate")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    @ApiModelProperty(required = true)
    private BigDecimal blendedRate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @JsonProperty("last_update")
    private Instant lastUpdate;

    @JsonProperty("soft_skills")
    @Builder.Default
    private List<TSoftSkillDTO> softSkills = new ArrayList<>();

    @JsonProperty("industries")
    @Builder.Default
    private Map<String, IndustryOpportunityInfoDTO> industries = new HashMap<>();

    @JsonProperty("english_level")
    @Builder.Default
    private Map<String, EnglishLevelOpportunityInfoDTO> englishLevelAverages = new HashMap<>();

    @JsonProperty(value = "additional_technologies_info_by_role", access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private Map<String, AdditionalTechnologyInfoDTO> additionalTechnologyInfoByRole = new HashMap<>();

    @JsonProperty(value = "main_technology_info_by_role", access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private Map<String, MainTechnologyOpportunityInfoDTO> mainTechnologyInfoByRole = new HashMap<>();

    @JsonProperty("timezone")
    private TimeZoneDTO timezone;

    @JsonProperty("team_ready")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate teamReady;

    @JsonProperty("talents")
    private List<TResumeDTO> talents;

    @JsonProperty("main_technologies")
    private List<TechnologyTeamDeckDTO> mainTechnologies;

    @JsonProperty("additional_technologies")
    private List<TechnologyTeamDeckDTO> additionalTechnologies;

    @ApiModelProperty(hidden = true)
    @JsonProperty(value = "proposal_uuid", access = JsonProperty.Access.READ_ONLY)
    private String proposalUuid;

    @ApiModelProperty(hidden = true)
    @JsonProperty(value = "team_request_id", access = JsonProperty.Access.READ_ONLY)
    private Long teamRequestId;

    @ApiModelProperty(hidden = true)
    @JsonProperty(value = "token", access = JsonProperty.Access.READ_ONLY)
    private String token;

    @JsonProperty("version_number")
    private int versionNumber;

    @JsonProperty("members_size")
    private int membersSize;

    @JsonProperty("status")
    private TeamRequestStatus status;


}
