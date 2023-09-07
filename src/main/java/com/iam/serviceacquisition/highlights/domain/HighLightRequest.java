package com.iam.serviceacquisition.highlights.domain;

import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchTalentFilterDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class HighLightRequest {

    private final List<TalentDTO> talents;

    private final SearchTalentFilterDTO filter;

    public static HighLightRequest from (List<TalentDTO> talents,  SearchTalentFilterDTO filter){
        return new HighLightRequest(talents, filter);
    }
}
