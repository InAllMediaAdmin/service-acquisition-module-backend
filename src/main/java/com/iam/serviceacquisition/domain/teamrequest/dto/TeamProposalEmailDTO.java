package com.iam.serviceacquisition.domain.teamrequest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Builder
@Data
public class TeamProposalEmailDTO {

    @ApiModelProperty(required = true)
    @JsonProperty("team_request_id")
    @NotNull
    private Long teamRequestId;

    @JsonProperty("cp_request_id")
    private Long cpRequestId;

    @JsonProperty("customer_lead_id")
    private Long customerLeadId;

    @ApiModelProperty(required = true)
    @JsonProperty("to")
    @Email(regexp="^$|.+@.+\\..+", message = "please provide a valid email address")
    private String to;

    @ApiModelProperty(required = true)
    @JsonProperty("subject")
    @NotNull
    private String subject;

    @JsonProperty("cc")
    private String cc;

    @ApiModelProperty(required = true)
    @JsonProperty("body")
    @NotNull
    @Size(max = 1500, message = "body field must be limited: Max 1500 characters")
    private String body;
}