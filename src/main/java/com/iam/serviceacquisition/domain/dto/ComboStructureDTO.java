package com.iam.serviceacquisition.domain.dto;

import com.iam.serviceacquisition.domain.search.SearchFilter;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ComboStructureDTO {
    private Long id;
    private List<SearchPositionSlotDTO> positionsSlots = new ArrayList<>();
    private SearchFilter filter;

}
