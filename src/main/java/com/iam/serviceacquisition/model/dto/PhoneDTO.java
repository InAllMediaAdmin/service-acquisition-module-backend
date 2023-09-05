package com.iam.serviceacquisition.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class PhoneDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @JsonProperty("country_code")
    @NotBlank
    @Pattern(regexp = "\\+?\\d{1,3}", message = "please provide a valid phone country code")
    private String countryCode;

    @JsonProperty("area_code")
    @NotBlank
    @Pattern(regexp = "\\d{1,5}", message = "please provide a valid phone area code")
    private String areaCode;

    @JsonProperty("phone_number")
    @NotBlank
    @Pattern(regexp = "\\d{4,8}", message = "please provide a valid phone number")
    private String phoneNumber;
}
