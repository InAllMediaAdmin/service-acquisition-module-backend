package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.Avatar;
import com.iam.serviceacquisition.common.enums.TalentLevel;
import com.iam.serviceacquisition.common.enums.TalentState;
import com.iam.serviceacquisition.domain.talentresume.dto.TCertificationDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TEducationDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TExperienceDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TSoftSkillDTO;
import com.iam.user.account.common.model.TalentRoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.iam.serviceacquisition.common.enums.TalentLevel.TECH_LEAD;
import static java.util.Objects.isNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonProperty(value = "main_technologies")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private List<TechnologyDTO> mainTechnologies;

    @JsonProperty(value = "secondary_technologies")
    @NotNull
    @Valid
    private List<TechnologyDTO> secondaryTechnologies;

    @JsonProperty(value = "roles")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private List<TalentRoleDTO> roles;

    @JsonProperty("city")
    private String city;

    @JsonProperty("availability_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityDate;

    @JsonProperty("time_zone")
    @NotNull
    @ApiModelProperty(required = true)
    private TimeZoneDTO timeZone;

    @JsonProperty("level")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private LevelDTO level;

    @JsonProperty("name")
    @NotBlank
    @ApiModelProperty(required = true)
    private String name;

    @JsonProperty("last_name")
    @NotBlank
    @ApiModelProperty(required = true)
    private String lastName;

    @JsonProperty("printable_name")
    private String printableName;

    @JsonInclude(NON_NULL)
    @JsonProperty("rate_per_hour")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    @ApiModelProperty(required = true)
    private BigDecimal rate;

    @ApiModelProperty(hidden = true)
    private Integer matching;

    @JsonProperty("gender")
    @Valid
    private GenderDTO gender;

    @JsonProperty("english_level")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private EnglishLevelDTO englishLevel;

    @JsonProperty("language_certification")
    private List<TalentLanguageCertificationDTO> languageCertifications = new ArrayList<>();

    @JsonProperty(value = "years_working_with_us")
    private Integer yearsWorkingWithUs;

    @JsonProperty(value = "experience")
    private Double experience;

    @Builder.Default
    @JsonProperty(value = "iam_actually_experience")
    private Boolean iamActuallyExperience = false;

    @Builder.Default
    @JsonProperty(value = "iam_actually_months")
    private Integer iamActuallyMonths = 0;

    @JsonProperty(value = "talent_experiences")
    @ApiModelProperty(hidden = true)
    private List<TExperienceDTO> experiences;

    @JsonProperty(value = "talent_educations")
    @ApiModelProperty(hidden = true)
    private List<TEducationDTO> educations;

    @JsonProperty(value = "talent_certifications")
    @ApiModelProperty(hidden = true)
    private List<TCertificationDTO> certifications;

    @JsonProperty(value = "talent_soft_skills")
    @ApiModelProperty(hidden = true)
    private List<TSoftSkillDTO> softSkills;

    @JsonProperty(value = "talent_technical_profiles")
    @ApiModelProperty(hidden = true)
    private List<TalentTechnicalProfilesDTO> technicalProfiles;

    @JsonProperty(value = "talent_highlight")
    @ApiModelProperty(hidden = true)
    private List<TalentHighlightDTO> highlight;

    @JsonProperty("avatar")
    private Avatar avatar;

    @JsonProperty("email")
    @Email(regexp = "^$|.+@.+\\..+", message = "please provide a valid email address")
    private String email;

    @JsonProperty("profile_linkedin")
    private String profileLinkedin;

    @JsonProperty("phone_number")
    private PhoneDTO phone;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private StateDTO state;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isTalentAdHoc() {
        return state.getId().equals(TalentState.ADHOC.getId());
    }

    @ApiModelProperty(hidden = true)
    private boolean leader;

    @ApiModelProperty(hidden = true)
    @JsonProperty("gpt_ia")
    private boolean gptIA;

    @JsonProperty(value = "last_update", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("email_iam")
    @Email(regexp = "^$|.+@.+\\..+", message = "please provide a valid email address")
    private String emailIam;

    @JsonProperty(value = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonProperty(value = "country")
    private CountryDTO country;

    @JsonProperty("profile_description")
    @Size(max = 700, message = "description field must be limited: Max 700 characters")
    private String profileDescription;

    @JsonProperty("interest_description")
    @Size(max = 700, message = "description field must be limited: Max 700 characters")
    private String interestDescription;

    @JsonProperty("main_industry")
    private IndustryDTO mainIndustry;

    @JsonProperty("offered_amount")
    private long offeredAmount;

    @JsonProperty(value = "main_technology")
    private TechnologyDTO mainTechnology;

    @JsonProperty("created_in_service_manager")
    private boolean createdInServiceManager;

    @JsonProperty("financial_information_id")
    private Long financialInfoId;

    
    public boolean isLeader() {
        if (this.getLevel() != null && this.getRoles() != null) return TalentLevel
                .fromId(this.getLevel().getId()).getLevel() >= TECH_LEAD.getLevel()
                || this.getRoles().stream().anyMatch(
                r -> (r.getDefaultRole().equals("PROJECT_MANAGER")));
        else return false;
    }

    /* used only in email templates */
    public int roundExperience() {
        return BigDecimal.valueOf(experience).setScale(0, RoundingMode.HALF_DOWN).intValue();
    }

    public Optional<IndustryDTO> getMainIndustryExperience() {
        if (isNull(experiences)){
            return Optional.empty();
        }
        return experiences.stream()
                .map(talentExperience -> {
                    LocalDate endDate = talentExperience.getEndDate() != null ? talentExperience.getEndDate() : LocalDate.now();
                    long duration = ChronoUnit.DAYS.between(talentExperience.getStartDate(), endDate);
                    return new AbstractMap.SimpleEntry<>(talentExperience.getIndustry(), duration);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        Long::sum))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}
