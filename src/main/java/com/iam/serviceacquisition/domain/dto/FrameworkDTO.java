package com.iam.serviceacquisition.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter @Setter @ToString
public class FrameworkDTO {

    private Long id;

    private String description;

    private TechnologyDTO technology;
}
