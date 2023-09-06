package com.iam.serviceacquisition.domain.teamrequest.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Data
public class CPRequestStatusDTO {

    private long id;

    private final String description;
}
