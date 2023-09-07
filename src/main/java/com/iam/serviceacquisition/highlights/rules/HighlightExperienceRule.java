package com.iam.serviceacquisition.highlights.rules;

import com.iam.serviceacquisition.common.enums.TalentLevel;
import com.iam.serviceacquisition.highlights.IHighlight;
import com.iam.serviceacquisition.highlights.domain.HighLightRequest;
import com.iam.serviceacquisition.highlights.domain.HighLightResult;
import com.iam.serviceacquisition.highlights.domain.experience.HighlightExperienceAverageDTO;
import com.iam.serviceacquisition.highlights.domain.experience.HighlightExperienceDTO;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchExperienceDTO;
import com.iam.user.account.common.model.TalentRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

import static com.iam.serviceacquisition.common.enums.TalentLevel.TECH_LEAD;
import static com.iam.serviceacquisition.highlights.domain.HighLightNames.EXPERIENCE;
import static java.lang.Math.round;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Builder
@AllArgsConstructor
public class HighlightExperienceRule implements IHighlight<HighLightRequest> {
    public static final Long PROJECT_MANAGER = 38L;
    public static final String PROJECT_MANAGER_LABEL = "Project Manager";

    @Override
    public HighLightResult calculate(HighLightRequest highlightRequest) {
        if (isNull(highlightRequest.getFilter()) || isNull(highlightRequest.getFilter().getSearchExperienceDTO())) {
            return HighLightResult.builder()
                    .highLightName(EXPERIENCE)
                    .highLightData(HighlightExperienceDTO.builder().must(false).build())
                    .build();
        }

        SearchExperienceDTO searchStrategyDTO = highlightRequest.getFilter().getSearchExperienceDTO();
        List<TalentDTO> talents = highlightRequest.getTalents();

        TalentRoleDTO pmRole = TalentRoleDTO.builder().id(PROJECT_MANAGER).label(PROJECT_MANAGER_LABEL).build();
        // leaders members

        List<TalentDTO> leaders = talents.stream()
                .filter(t -> t.getRoles().contains(pmRole)
                        || TalentLevel.fromId(t.getLevel().getId()).getLevel() >= TECH_LEAD.getLevel())
                .collect(toList());

        // other members
        List<TalentDTO> teamsMembers = talents.stream().filter(t -> !leaders.contains(t)).collect(toList());

        HighlightExperienceDTO highlightExperienceDTO = HighlightExperienceDTO.builder()
                .leaderAverage(HighlightExperienceAverageDTO.builder()
                        .average(calculateExperiencePercentageByTeam(leaders)).build())
                .teamAverage(HighlightExperienceAverageDTO.builder()
                        .average(calculateExperiencePercentageByTeam(teamsMembers)).build())
                .must(searchStrategyDTO.getMust())
                .build();

        return HighLightResult.builder()
                .highLightName(EXPERIENCE)
                .highLightData(highlightExperienceDTO)
                .build();
    }

    private int calculateExperiencePercentageByTeam(List<TalentDTO> talents) {
        return (int) round(talents.stream().map(TalentDTO::getExperience).mapToDouble(v -> v).average().orElse(0D));
    }
}
