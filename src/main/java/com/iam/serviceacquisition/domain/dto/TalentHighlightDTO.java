package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentHighlightDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("talent_id")
    @NonNull
    @ApiModelProperty(required = true)
    private Long talentId;

    @JsonProperty("talent_experience_id")
    @NonNull
    @ApiModelProperty(required = true)
    private Long talentExperienceId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("description")
    private String description;


}

