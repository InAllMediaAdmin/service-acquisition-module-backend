package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AvatarDTO {

    private int id;

    @JsonProperty(value = "gender")
    private GenderDTO genderDTO;

}
