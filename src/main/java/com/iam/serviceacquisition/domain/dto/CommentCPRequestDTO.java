package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.user.account.common.model.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class CommentCPRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("body")
    @ApiModelProperty(required = true)
    private String body;

    @JsonProperty("type")
    @ApiModelProperty(required = true)
    private String activityType;

    @JsonProperty(value = "author", access = JsonProperty.Access.READ_ONLY)
    private UserDTO activityAuthor;

    @JsonProperty("cp_request_id")
    private Long activityCPRequestId;

    @JsonProperty("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant activityCreated;

}
