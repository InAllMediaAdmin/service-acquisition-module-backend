package com.iam.serviceacquisition.highlights.rules;

import com.iam.serviceacquisition.highlights.IHighlight;
import com.iam.serviceacquisition.highlights.domain.HighLightNames;
import com.iam.serviceacquisition.highlights.domain.HighLightRequest;
import com.iam.serviceacquisition.highlights.domain.HighLightResult;
import com.iam.serviceacquisition.highlights.domain.industry.HighLightIndustryDTO;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchIndustryDTO;
import lombok.Builder;


import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.util.CollectionUtils.isEmpty;

@Builder
public class HighLightIndustryRule implements IHighlight<HighLightRequest> {
    @Override
    public HighLightResult calculate(HighLightRequest highlightRequest) {
        HighLightIndustryDTO highLightIndustryLevel = HighLightIndustryDTO.builder().must(false).build();

        boolean isIndustryMustApplicable = nonNull(highlightRequest.getFilter())
                && nonNull(highlightRequest.getFilter().getSearchIndustryDTO());

        List<TalentDTO> talents = highlightRequest.getTalents();

        if (!isEmpty(talents) && isIndustryMustApplicable){
            SearchIndustryDTO filterIndustry = highlightRequest.getFilter().getSearchIndustryDTO();

            highLightIndustryLevel = HighLightIndustryDTO.builder()
                    .id(filterIndustry.getId())
                    .percent((int) Math.round(getPercent(filterIndustry.getId(), talents)))
                    .must(filterIndustry.getMust())
                    .build();
        }

        return HighLightResult.builder()
                .highLightName(HighLightNames.INDUSTRY)
                .highLightData(highLightIndustryLevel)
                .build();
    }

    private double getPercent(Long industryId, List<TalentDTO> talents) {
        double average = talents.stream()
                .map(TalentDTO::getExperiences)
                .filter(exp -> isNotEmpty(exp) && exp.stream()
                        .anyMatch(e -> industryId.equals(e.getIndustry().getId())))
                .count();

        return (average / talents.size()) * 100;
    }
}
