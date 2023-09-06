package com.iam.serviceacquisition.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
public class TeamPositionDTO {

    private long id;
    private String name;
    private String index;
}
