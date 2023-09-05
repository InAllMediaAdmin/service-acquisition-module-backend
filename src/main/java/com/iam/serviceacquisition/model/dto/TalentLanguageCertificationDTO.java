package com.iam.serviceacquisition.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@Data
public class TalentLanguageCertificationDTO {

    private Long id;

    @JsonProperty(value = "year")
    private int year;

    @JsonProperty(value = "institution")
    private String institution;

    @JsonProperty(value = "certification")
    private String certification;

    @JsonProperty("talent_id")
    @NotNull
    @ApiModelProperty(required = true)
    private Long talentId;

}
