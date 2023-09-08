package com.iam.serviceacquisition.domain.search;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SearchFilter {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    private Integer englishValue;

    private Boolean englishMust;

    @Column(name = "rate_per_hour", precision = 5, scale = 2)
    private BigDecimal rateValue;

    private Boolean rateMust;

    private Integer experienceValue;

    private Boolean experienceMust;

    private Integer yearsWorkingValue;

    private Boolean yearsWorkingMust;

    private Long industryValue;

    private Boolean industryMust;

    @ElementCollection
    @CollectionTable(name = "search_strategies_values")
    private List<Long> strategiesValues;

    private Boolean strategyMust;

    // extra methods
    public boolean isEnglishMustApplicable() {
        return isEnglishLevelAvailable() && englishMust == TRUE;
    }

    public boolean isEnglishLevelAvailable() {
        return nonNull(englishValue);
    }

    public boolean isRateAvailable() {
        return nonNull(rateValue);
    }

    public boolean isRateMustApplicable() {
        return isRateAvailable() && rateMust == TRUE;
    }

    public boolean isIndustryAvailable() {
        return nonNull(industryValue);
    }

    public boolean isIndustryMustApplicable() {
        return isIndustryAvailable() && industryMust == TRUE;
    }

    public boolean isStrategyAvailable() {
        return isNotEmpty(strategiesValues);
    }

    public boolean isStrategyMustApplicable() {
        return isStrategyAvailable() && strategyMust == TRUE;
    }

    public boolean isExperienceAvailable() {
        return nonNull(experienceValue);
    }

    public boolean isExperienceMustApplicable() {
        return isExperienceAvailable() && experienceMust == TRUE;
    }

    public boolean doApply() {
        return isEnglishLevelAvailable() ||
                isRateAvailable() || isExperienceAvailable() || isYearsWithUsAvailable()
                || isIndustryAvailable() || isStrategyAvailable();
    }

    public boolean isYearsWithUsAvailable() {
        return nonNull(yearsWorkingValue);
    }

    public boolean isYearsWithUsMustApplicable() {
        return isYearsWithUsAvailable() && yearsWorkingMust == TRUE;
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
