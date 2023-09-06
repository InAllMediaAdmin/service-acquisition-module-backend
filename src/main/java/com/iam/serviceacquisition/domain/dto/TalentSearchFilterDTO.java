package com.iam.serviceacquisition.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Builder
@Getter
@Setter
public class TalentSearchFilterDTO {

    private Long roleId;
    private Long levelId;
    private Long technologyId;
    private List<Long> secondariesId;
    private Integer englishLevel;
    private Integer timezoneId;
    private BigDecimal rate;
    private Integer yearsExperience;
    private String searchValue;

    private List<Long> industryId;

    private LocalDate availabilityDate;

    private List<Long> idInAllTechnologies;

    public boolean isRoleIdAvailable() {
        return nonNull(roleId);
    }

    public boolean isLevelAvailable() {
        return nonNull(levelId);
    }

    public boolean isTechnologyAvailable() {
        return nonNull(technologyId);
    }

    public boolean hasSecondaryTechnologies() {
        return isNotEmpty(secondariesId);
    }

    public boolean isEnglishAvailable() {
        return nonNull(englishLevel);
    }

    public boolean isTimezoneAvailable() {
        return nonNull(timezoneId);
    }

    public boolean isRateAvailable() {
        return nonNull(rate);
    }

    public boolean isYearsExperienceAvailable() {
        return nonNull(yearsExperience);
    }

    public boolean isSearchValuePresent() {
        return isNotBlank(searchValue);
    }
}
