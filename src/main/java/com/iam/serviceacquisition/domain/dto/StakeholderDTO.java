package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Getter
@Setter
public class StakeholderDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "first_name", required = true)
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    private String lastName;

    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonProperty(value = "phone")
    private PhoneDTO phone;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "client_id")
    private Long clientId;

}
