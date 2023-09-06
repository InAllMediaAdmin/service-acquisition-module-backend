package com.iam.serviceacquisition.domain.talentresume.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.IndustryDTO;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.dto.TechnologyDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TExperienceDTO {

    private Long id;

    @JsonProperty("talent_id")
    @NotNull
    @ApiModelProperty(required = true)
    private TalentDTO talent;

    @JsonProperty("start_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(required = true)
    private final LocalDate startDate;

    @JsonProperty("end_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private final LocalDate endDate;

    @JsonProperty("role")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String role;

    @JsonProperty(value = "industry")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private final IndustryDTO industry;

    @JsonProperty(value = "main_technology")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private final TechnologyDTO mainTechnology;

    @JsonProperty(value = "main_technology_years")
    private final Double mainTechnologyYears;

    @JsonProperty(value = "additional_technologies")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private final List<TechnologyDTO> additionalTechnologies  = new ArrayList<>();

    @JsonProperty("company")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String company;

    @JsonProperty("description")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String description;

    @JsonProperty("description_plain")
    @Size(min= 100, max = 500, message = "description field must be limited: Min 100 - Max 500 characters")
    private final String description_plain;

    @JsonProperty("activities")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String activities;

    @JsonProperty("activities_plain")
    @Size(min= 100, max = 2000, message = "activities field must be limited: Min 100 - Max 2000 characters")
    private final String activities_plain;

    @JsonProperty("iam_experience")
    private boolean iamExperience;

    @JsonProperty("remote_work")
    private boolean remoteWork;

    @JsonProperty("assigned_to")
    private String assignedTo;

    @JsonProperty("currently")
    private boolean currently;

    @JsonProperty(value = "talent_soft_skills")
    private final List<TSoftSkillDTO> talentSoftSkills  = new ArrayList<>();
}
