package com.iam.serviceacquisition.domain.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class AdditionalTechnologyInfoDTO {
    @Builder.Default
    private List<TalentDTO> talents =  new ArrayList<>();
    @Builder.Default
    private Set<TechnologyDTO> technologies =  new HashSet<>();
    @Builder.Default
    private double averageExperience = 0;
}
