package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.EnglishLevel;
import com.iam.serviceacquisition.domain.activity.ActivityDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchTalentFilterDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TExperienceDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TResumeDTO;
import com.iam.serviceacquisition.domain.talentresume.dto.TSoftSkillDTO;
import com.iam.user.account.common.model.TalentRoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TeamPositionSlotDTO {

    private Long id;

    @JsonProperty(value = "talent")
    private TalentDTO talent;

    @JsonProperty(value = "talent_resume")
    private TResumeDTO talentResume;

    @JsonProperty(value = "search_position_slot")
    private SearchPositionSlotDTO searchPositionSlot;

    @JsonProperty(value = "match_filter_attrs")
    @ApiModelProperty(hidden = true)
    private Map<String, Object> matchFilterAttrs;

    @JsonProperty(value = "no_match_filter_attrs")
    @ApiModelProperty(hidden = true)
    private Map<String, Object> missingFilterAttrs;

    @JsonProperty("activities")
    private List<ActivityDTO> activities;

    @JsonProperty("unread_comment_by_current_user")
    private boolean unreadCommentByCurrentUser;

    @JsonProperty("team_id")
    private Long teamId;

    @JsonIgnore
    private BigDecimal saleRate;

    public void addMatchingFilterAttrs(SearchRequestDTO searchRequestDTO) {
        if (nonNull(talent) && nonNull(searchRequestDTO) && (nonNull(searchPositionSlot))) {
            matchFilterAttrs = new HashMap<>();
            missingFilterAttrs = new HashMap<>();

            // time zone
            float timeZoneTalent = talent.getTimeZone().getTimeZoneOffset();
            float timeZoneCriteria = searchRequestDTO.getTimeZone().getTimeZoneOffset();
            if (timeZoneTalent >= timeZoneCriteria - 1 &&
                    timeZoneTalent <= timeZoneCriteria + 1) {
                matchFilterAttrs.put("timeZone", timeZoneTalent);
            } else {
                missingFilterAttrs.put("timeZone", timeZoneCriteria);
            }

            // availability date
            LocalDate availabilityDate = talent.getAvailabilityDate();
            LocalDate startDate = searchRequestDTO.getStartDate();
            if (availabilityDate.isBefore(startDate.plusDays(1))) {
                matchFilterAttrs.put("availabilityDate", availabilityDate);
            } else {
                missingFilterAttrs.put("availabilityDate", startDate);
            }

            // seniority
            LevelDTO talentLevel = talent.getLevel();
            LevelDTO requiredLevel = searchPositionSlot.getSeniority();
            if (talentLevel.getId() == requiredLevel.getId()) {
                matchFilterAttrs.put("seniority", talentLevel);
            } else {
                missingFilterAttrs.put("seniority", requiredLevel);
            }


            Optional<TalentRoleDTO> role = talent.getRoles().stream().filter(r -> r.getId()
                    .equals(searchPositionSlot.getRole().getId())).findFirst();

            if (role.isPresent()) {
                matchFilterAttrs.put("role", role.get());
            } else {
                missingFilterAttrs.put("role", searchPositionSlot.getRole());
            }

            // main techs
            List<Long> requiredMainTechs = searchPositionSlot.getMainTechnologies().stream()
                    .map(TechnologyDTO::getId).collect(toList());
            List<Long> talentMainTechs = talent.getMainTechnologies().stream()
                    .map(TechnologyDTO::getId).collect(toList());
            List<TechnologyDTO> matchMainTechs = talent.getMainTechnologies().stream()
                    .filter(t -> requiredMainTechs.contains(t.getId())).collect(toList());
            if (!isEmpty(matchMainTechs)) {
                matchFilterAttrs.put("mainTechnologies", matchMainTechs);
            }
            List<TechnologyDTO> missingMainTechs = searchPositionSlot.getMainTechnologies().stream()
                    .filter(t -> !talentMainTechs.contains(t.getId())).collect(toList());
            if (!isEmpty(missingMainTechs)) {
                missingFilterAttrs.put("mainTechnologies", missingMainTechs);
            }

            // secondary techs
            List<Long> requiredSecTechs = searchPositionSlot.getSecondaryTechnologies().stream()
                    .map(TechnologyDTO::getId).collect(toList());
            List<Long> talentSecTechs = talent.getSecondaryTechnologies().stream()
                    .map(TechnologyDTO::getId).collect(toList());
            List<TechnologyDTO> matchSecondaryTechs = talent.getSecondaryTechnologies().stream()
                    .filter(t -> requiredSecTechs.contains(t.getId())).collect(toList());
            if (!isEmpty(matchSecondaryTechs)) {
                matchFilterAttrs.put("secondaryTechnologies", matchSecondaryTechs);
            }
            List<TechnologyDTO> missingSecondaryTechs = searchPositionSlot.getSecondaryTechnologies().stream()
                    .filter(t -> !talentSecTechs.contains(t.getId())).collect(toList());
            if (!isEmpty(missingSecondaryTechs)) {
                missingFilterAttrs.put("secondaryTechnologies", missingSecondaryTechs);
            }

            // talent filter
            processSearchTalentFilter(searchRequestDTO.getFilter());
        }

    }

    private void processSearchTalentFilter(SearchTalentFilterDTO filter) {
        if (nonNull(talent) && nonNull(filter)) {
            if (nonNull(filter.getSearchEnglishLevelDTO())
                    && nonNull(filter.getSearchEnglishLevelDTO().getEnglishLevel())) {
                EnglishLevel filterEnglishLevel = EnglishLevel.fromId(filter.getSearchEnglishLevelDTO().getEnglishLevel());
                EnglishLevel talentEnglishLevel = EnglishLevel.fromId(talent.getEnglishLevel().getId());
                if (filterEnglishLevel.getId() <= talentEnglishLevel.getId()) {
                    matchFilterAttrs.put("englishLevel", talentEnglishLevel.getLabel());
                } else {
                    missingFilterAttrs.put("englishLevel", filterEnglishLevel.getLabel());
                }
            }
            if (nonNull(filter.getSearchExperienceDTO())
                    && nonNull(filter.getSearchExperienceDTO().getExperience())) {
                double talentExperience = talent.getExperience();
                int filterExperience = filter.getSearchExperienceDTO().getExperience();
                if (talentExperience >= filterExperience) {
                    matchFilterAttrs.put("experience", talentExperience);
                } else {
                    missingFilterAttrs.put("experience", filterExperience);
                }
            }
            if (nonNull(filter.getSearchRateDTO())
                    && nonNull(filter.getSearchRateDTO().getRate()) && nonNull(talent.getRate())) {
                double talentRate = talent.getRate().doubleValue();
                double filterRate = filter.getSearchRateDTO().getRate().doubleValue();
                if (talentRate != filterRate) {
                    matchFilterAttrs.put("rate", talent.getRate());
                } else {
                    missingFilterAttrs.put("rate", filter.getSearchRateDTO().getRate());
                }
            }
            if (nonNull(filter.getSearchIndustryDTO())
                    && nonNull(filter.getSearchIndustryDTO().getId())) {

                Optional<IndustryDTO> industry = Optional.empty();
                if (!isEmpty(talent.getExperiences())) {
                    industry = talent.getExperiences().stream().map(TExperienceDTO::getIndustry).filter(i -> i.getId()
                            .equals(filter.getSearchIndustryDTO().getId())).findFirst();
                }

                if (industry.isPresent()) {
                    matchFilterAttrs.put("industry", industry.get());
                } else {
                    missingFilterAttrs.put("industry", filter.getSearchIndustryDTO().getId());
                }
            }
            if (nonNull(filter.getSearchStrategyDTO())
                    && nonNull(filter.getSearchStrategyDTO().getId())) {
                List<StrategyDTO> strategies = isEmpty(talent.getSoftSkills()) ? Collections.emptyList()
                        : talent.getSoftSkills().stream().map(TSoftSkillDTO::getStrategy)
                        .collect(Collectors.toList());
                List<Long> filterStrategies = filter.getSearchStrategyDTO().getId();

                List<StrategyDTO> matchStrategies = strategies.stream()
                        .filter(s -> filterStrategies.contains(s.getId())).collect(toList());
                if (!isEmpty(matchStrategies)) {
                    matchFilterAttrs.put("strategies", matchStrategies);
                }
                List<Long> missingStrategies = filterStrategies.stream().filter(fs ->
                        strategies.stream().noneMatch(s -> Objects.equals(s.getId(), fs))).collect(toList());
                if (!isEmpty(missingStrategies)) {
                    missingFilterAttrs.put("strategies", missingStrategies);
                }
            }
        }
    }

    /**
     * Don't remove. Used for emails notification with thymeleaf template
     */
    public String getTalentType() {
        if (nonNull(searchPositionSlot) && nonNull(searchPositionSlot.getType())) {
            return searchPositionSlot.getType().name();
        }
        return "";
    }

}
