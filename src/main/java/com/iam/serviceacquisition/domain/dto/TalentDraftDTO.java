package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.Avatar;
import com.iam.serviceacquisition.common.enums.TalentState;
import com.iam.serviceacquisition.domain.talentresume.dto.TCertificationDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TEducationDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TExperienceDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TSoftSkillDTO;
import com.iam.user.account.common.model.TalentRoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import jakarta.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentDraftDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @JsonProperty("avatar")
    private Avatar avatar;

    @JsonProperty("name")
    @NotBlank(message = "Name may not be black")
    @ApiModelProperty(required = true)
    private String name;

    @JsonProperty("last_name")
    @NotBlank
    @ApiModelProperty(required = true)
    private String lastName;

    @JsonProperty("email")
    @Email(regexp="^$|.+@.+\\..+", message = "please provide a valid email address")
    private String email;

    @JsonProperty("email_iam")
    @Email(regexp = "^$|.+@.+\\..+", message = "please provide a valid email address")
    private String emailIam;

    @JsonProperty(value = "main_technologies")
    private List<TechnologyDTO> mainTechnologies;

    @JsonProperty(value = "secondary_technologies")
    private List<TechnologyDTO> secondaryTechnologies;

    @JsonProperty(value = "roles")
    private List<TalentRoleDTO> roles;

    @JsonProperty("city")
    private String city;

    @JsonProperty("availability_date")
    private LocalDate availabilityDate;

    @JsonProperty("time_zone")
    private TimeZoneDTO timeZone;

    @JsonProperty("level")
    private LevelDTO level;

    @JsonProperty("printable_name")
    private String printableName;

    @JsonProperty("rate_per_hour")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal rate;

    @ApiModelProperty(hidden = true)
    private int matching;

    @JsonProperty("gender")
    private GenderDTO gender;

    @JsonProperty("english_level")
    private EnglishLevelDTO englishLevel;

    @JsonProperty("language_certification")
    private List<TalentLanguageCertificationDTO> languageCertifications = new ArrayList<>();

    @JsonProperty(value = "industries")
    private List<IndustryDTO> industries;

    @JsonProperty(value = "years_working_with_us")
    private int yearsWorkingWithUs;

    @JsonProperty(value = "experience")
    private int experience;

    @JsonProperty(value = "strategies")
    private List<StrategyDTO> strategies;

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

    @JsonProperty("profile_linkedin")
    private String profileLinkedin;

    @JsonProperty("phone_number")
    private PhoneDTO phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private StateDTO state;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isTalentAdHoc() {
        return state.getId().equals(TalentState.ADHOC.getId());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(hidden = true)
    private SearchPositionSlotDTO searchPositionSlotDTO;

    @JsonProperty(value = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "country")
    private CountryDTO country;

    @JsonProperty("profile_description")
    @Size(max = 700, message = "description field must be limited: Max 700 characters")
    private String profileDescription;

    @JsonProperty("interest_description")
    @Size(max = 400, message = "description field must be limited: Max 400 characters")
    private String interestDescription;

}
