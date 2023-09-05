package com.iam.serviceacquisition.model.dto.talentresume;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.model.dto.TalentDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class TCertificationDTO {

    private Long id;

    @JsonProperty("talent_id")
    @NotNull
    @ApiModelProperty(required = true)
    private TalentDTO talent;

    @JsonProperty("start_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(required = true)
    private final LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(required = true)
    private final LocalDate endDate;

    @JsonProperty("school")
    @NotBlank
    @ApiModelProperty(required = true)
    private final String school;

    @JsonProperty("degree")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private final DegreeCertDTO degree;
}
