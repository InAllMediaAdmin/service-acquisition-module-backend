package com.iam.serviceacquisition.domain.dto;

import lombok.*;

@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Data
@Builder
public class SearchPositionSlotTypeDTO {

    private int id;
    private String description;
}
