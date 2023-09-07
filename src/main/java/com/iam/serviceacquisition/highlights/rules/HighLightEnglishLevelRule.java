package com.iam.serviceacquisition.highlights.rules;

import com.iam.serviceacquisition.common.enums.EnglishLevel;
import com.iam.serviceacquisition.highlights.IHighlight;
import com.iam.serviceacquisition.highlights.domain.HighLightNames;
import com.iam.serviceacquisition.highlights.domain.HighLightRequest;
import com.iam.serviceacquisition.highlights.domain.HighLightResult;
import com.iam.serviceacquisition.highlights.domain.englishlevel.EnglishLevelAvgDTO;
import com.iam.serviceacquisition.highlights.domain.englishlevel.HighLightEnglishLevelDTO;
import com.iam.serviceacquisition.domain.dto.EnglishLevelDTO;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchEnglishLevelDTO;
import lombok.Builder;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.iam.serviceacquisition.common.enums.EnglishLevel.INTERMEDIATE;
import static com.iam.serviceacquisition.common.enums.EnglishLevel.UPPER_INTERMEDIATE;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.util.CollectionUtils.isEmpty;

@Builder
public class HighLightEnglishLevelRule implements IHighlight<HighLightRequest> {
    @Override
    public HighLightResult calculate(HighLightRequest highLightRequest) {
        HighLightEnglishLevelDTO highLightEnglishLevel = HighLightEnglishLevelDTO.builder().must(false).build();

        boolean isEnglishMustApplicable = nonNull(highLightRequest.getFilter())
                && nonNull(highLightRequest.getFilter().getSearchEnglishLevelDTO());

        List<TalentDTO> talents = highLightRequest.getTalents();

        if (!isEmpty(talents) && isEnglishMustApplicable){
            SearchEnglishLevelDTO filterEnglishLevel = highLightRequest.getFilter().getSearchEnglishLevelDTO();
            highLightEnglishLevel = HighLightEnglishLevelDTO.builder()
                    .avgLeaders(getAvgLeaders(talents))
                    .avgTeam(getAvgTeam(talents))
                    .must(filterEnglishLevel.getMust())
                    .build();
        }

        return HighLightResult.builder()
                .highLightName(HighLightNames.ENGLISH_LEVEL)
                .highLightData(highLightEnglishLevel)
                .build();
    }

    private EnglishLevelAvgDTO getAvgLeaders (List<TalentDTO> talents){
        List<TalentDTO> filterTalents  = talents.stream()
                .filter(TalentDTO::isLeader)
                .collect(Collectors.toList());
        if (filterTalents.isEmpty()){
            return null;
        }
        Map<EnglishLevel, Long> map = getGroupingTalents(filterTalents);
        EnglishLevel avgEnglishLevel = getAvgEnglishLevel(map);
        return EnglishLevelAvgDTO
                .builder()
                .englishLevel(avgEnglishLevel.getLabel())
                .percent((int) Math.round(map.get(avgEnglishLevel) * 100d / filterTalents.size()))
                .build();
    }

    private EnglishLevelAvgDTO getAvgTeam (List<TalentDTO> talents){
        Map<EnglishLevel, Long> map = getGroupingTalents(talents);
        EnglishLevel avgEnglishLevel = getAvgEnglishLevel(map);
        return EnglishLevelAvgDTO
                .builder()
                .englishLevel(avgEnglishLevel.getLabel())
                .percent((int) Math.round(map.get(avgEnglishLevel) * 100d / talents.size()))
                .build();
    }

    private EnglishLevel getAvgEnglishLevel (Map<EnglishLevel, Long> map){
        Optional<Map.Entry<EnglishLevel, Long>> entry = map.entrySet().stream().max(Map.Entry.comparingByValue());
        if (entry.isPresent()){
            return map.entrySet().stream()
                    .filter(e -> e.getValue().equals(entry.get().getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                    .stream()
                    .max(Comparator.comparing(EnglishLevel::getId))
                    .orElse(entry.get().getKey());
        }
        return UPPER_INTERMEDIATE;
    }

    private Map<EnglishLevel, Long> getGroupingTalents (List<TalentDTO> talents){
        return talents.stream().collect(groupingBy(t -> EnglishLevel.fromId(
                Optional.ofNullable(t.getEnglishLevel())
                        .orElse(EnglishLevelDTO.builder().id(INTERMEDIATE.getId()).build())
                        .getId()), counting()));
    }
}
