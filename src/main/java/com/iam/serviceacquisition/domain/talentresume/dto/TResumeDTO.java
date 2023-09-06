package com.iam.serviceacquisition.domain.talentresume.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.*;
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
public class TResumeDTO {

    private Long id;

    @JsonProperty("adhoc")
    @NotNull
    @ApiModelProperty(required = true)
    private final Boolean adhoc;

    @JsonProperty("name")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String name;

    @JsonProperty("real_name")
    private final String realName;

    @JsonProperty("avatar")
    private AvatarDTO avatar;

    @JsonProperty("status")
    private TalentResumeStateDTO status;

    @JsonProperty("role")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String role;

    @JsonProperty("level")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private final LevelDTO level;

    @JsonProperty("english_level")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private EnglishLevelDTO englishLevel;

    @JsonProperty("language_certification")
    private List<TalentLanguageCertificationDTO> languageCertifications = new ArrayList<>();

    @JsonProperty("team_position_slot")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private TResumePositionSlotDTO teamPositionSlot;

    @JsonProperty("experiences")
    private final List<TExperienceDTO> experiences;

    @JsonProperty("educations")
    private final List<TEducationDTO> educations;

    @JsonProperty("certifications")
    private final List<TCertificationDTO> certifications;

    @JsonProperty("industry_expertises")
    private final List<TIndustryExpertiseDTO> industryExpertises;

    @JsonProperty("soft_skills")
    private final List<TSoftSkillDTO> softSkills;

    @JsonProperty("profile_summary")
    @Size(max = 400, message = "profile_summary field must be limited: Max 300 characters")
    private final String profileSummary;

    @JsonProperty("interests")
    @Size(max = 400, message = "interests field must be limited: Max 250 characters")
    private final String interests;

    @JsonProperty("highlight")
    private final TResumeHighlightDTO resumeHighlight;

    @JsonProperty("availability_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(required = true)
    private LocalDate availabilityDate;
}
