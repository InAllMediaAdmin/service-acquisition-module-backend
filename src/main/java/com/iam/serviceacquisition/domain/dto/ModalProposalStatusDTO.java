package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ModalProposalStatusDTO {

    private int id;

    @JsonProperty(value = "description")
    private final String description;
}
