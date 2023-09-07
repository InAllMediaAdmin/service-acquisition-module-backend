package com.iam.serviceacquisition.domain.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenderDTO {

    private Integer id;
    private String description;
}
