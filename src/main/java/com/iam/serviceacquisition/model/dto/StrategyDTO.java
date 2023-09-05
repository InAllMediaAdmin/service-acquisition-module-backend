package com.iam.serviceacquisition.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class StrategyDTO {

    private Long id;
    private String description;
}
