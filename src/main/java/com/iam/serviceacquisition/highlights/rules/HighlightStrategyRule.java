package com.iam.serviceacquisition.highlights.rules;

import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchStrategyDTO;
import com.iam.serviceacquisition.highlights.IHighlight;
import com.iam.serviceacquisition.highlights.domain.HighLightNames;
import com.iam.serviceacquisition.highlights.domain.HighLightRequest;
import com.iam.serviceacquisition.highlights.domain.HighLightResult;
import com.iam.serviceacquisition.highlights.domain.strategy.HighlightStrategyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Builder
@AllArgsConstructor
public class HighlightStrategyRule implements IHighlight<HighLightRequest> {

    @Override
    public HighLightResult calculate(HighLightRequest highlightRequest) {
        if (isNull(highlightRequest.getFilter()) || isNull(highlightRequest.getFilter().getSearchStrategyDTO())) {
            return HighLightResult.builder()
                    .highLightName(HighLightNames.STRATEGY_LEVEL)
                    .highLightData(HighlightStrategyDTO.builder().must(false).build())
                    .build();
        }

        SearchStrategyDTO searchStrategyDTO = highlightRequest.getFilter().getSearchStrategyDTO();
        List<TalentDTO> talents = highlightRequest.getTalents();

        Map<Long, Integer> strategiesPercentages = searchStrategyDTO.getId().stream()
                .collect(Collectors.toMap(i -> i, u -> (int) Math.round(calculatePercentageByStrategyId(u, talents))));

        return HighLightResult.builder()
                .highLightName(HighLightNames.STRATEGY_LEVEL)
                .highLightData(highlightStrategyDTO(searchStrategyDTO, strategiesPercentages))
                .build();
    }

    private HighlightStrategyDTO highlightStrategyDTO(SearchStrategyDTO searchStrategyDTO, Map<Long, Integer> strategiesPercentages) {
        return HighlightStrategyDTO.builder()
                .must(searchStrategyDTO.getMust())
                .strategies(strategiesPercentages)
                .build();
    }

    private double calculatePercentageByStrategyId(Long strategyID, List<TalentDTO> talents) {
        int teamSizes = talents.size();
        double average = talents.stream()
                .map(TalentDTO::getSoftSkills)
                .filter(sft -> isNotEmpty(sft) && sft.stream()
                        .anyMatch(s -> strategyID.equals(s.getStrategy().getId())))
                .count();

        return (average / teamSizes) * 100;
    }
}
