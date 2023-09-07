package com.iam.serviceacquisition.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import lombok.*;

import java.util.Comparator;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchTalentFilterDTO {

    @JsonProperty("english")
    private SearchEnglishLevelDTO searchEnglishLevelDTO;

    @JsonProperty("rate")
    private SearchRateDTO searchRateDTO;

    @JsonProperty("experience")
    private SearchExperienceDTO searchExperienceDTO;

    @JsonProperty(value = "years_working_with_us")
    private SearchYearWithUsDTO searchYearWithUsDTO;

    @JsonProperty(value = "industry")
    private SearchIndustryDTO searchIndustryDTO;

    @JsonProperty(value = "strategy")
    private SearchStrategyDTO searchStrategyDTO;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnglishLevelAvailable() {
        return nonNull(searchEnglishLevelDTO) && nonNull(searchEnglishLevelDTO.getEnglishLevel());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnglishMustApplicable() {
        return isEnglishLevelAvailable() && searchEnglishLevelDTO.getMust() == TRUE;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isRateAvailable() {
        return nonNull(searchRateDTO) && nonNull(searchRateDTO.getRate());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isRateMustApplicable() {
        return isRateAvailable() && searchRateDTO.getMust() == TRUE;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isExperienceAvailable() {
        return nonNull(searchExperienceDTO) && nonNull(searchExperienceDTO.getExperience());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isExperienceMustApplicable() {
        return isExperienceAvailable() && searchExperienceDTO.getMust() == TRUE;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isYearsWithUsAvailable() {
        return nonNull(searchYearWithUsDTO) && nonNull(searchYearWithUsDTO.getYears());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isYearsWithUsMustApplicable() {
        return isYearsWithUsAvailable() && searchYearWithUsDTO.getMust() == TRUE;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isIndustryAvailable() {
        return nonNull(searchIndustryDTO) && nonNull(searchIndustryDTO.getId());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isIndustryMustApplicable() {
        return isIndustryAvailable() && searchIndustryDTO.getMust() == TRUE;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isStrategyAvailable() {
        return nonNull(searchStrategyDTO) && !isEmpty(searchStrategyDTO.getId());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isStrategyMustApplicable() {
        return isStrategyAvailable() && searchStrategyDTO.getMust() == TRUE;
    }

    public boolean doApply() {
        return isEnglishLevelAvailable() ||
                isRateAvailable() || isExperienceAvailable() || isYearsWithUsAvailable()
                || isIndustryAvailable() || isStrategyAvailable();
    }

    public void filterToUpgradeTalents(List<TalentDTO> talents) {
        if (isRateAvailable()) {
            talents.sort(Comparator.comparingDouble(t -> t.getRate().doubleValue()));
        }
        if (isEnglishLevelAvailable()) {
            talents.sort((t1, t2) -> Integer.compare(t2.getEnglishLevel().getId(), t1.getEnglishLevel().getId()));
        }
        if (isExperienceAvailable()) {
            talents.sort(Comparator.comparingDouble(TalentDTO::getExperience).reversed());
        }
    }

    public int filtersApplied() {
        int amount = 0;

        if (isEnglishLevelAvailable()) amount++;
        if (isRateAvailable()) amount++;
        if (isExperienceAvailable()) amount++;
        if (isYearsWithUsAvailable()) amount++;
        if (isIndustryAvailable()) amount++;
        if (isStrategyAvailable()) amount++;

        return amount;
    }

    public int filtersMustApplied() {
        int amount = 0;

        if (isEnglishMustApplicable()) amount++;
        if (isRateMustApplicable()) amount++;
        if (isExperienceMustApplicable()) amount++;
        if (isYearsWithUsMustApplicable()) amount++;
        if (isIndustryMustApplicable()) amount++;
        if (isStrategyMustApplicable()) amount++;

        return amount;
    }
}
