package com.iam.serviceacquisition.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TimeZoneDTO {

    private long id;

    @JsonProperty(value = "timeZoneOffset")
    private float timeZoneOffset;

    @JsonProperty(value = "shortDescription")
    private String shortDescription;

    @JsonProperty(value = "mediumDescription")
    private String mediumDescription;

    @JsonProperty(value = "longDescription")
    private String longDescription;

    @JsonProperty("country")
    private String country;

    public TimeZoneDTO(long id) {
        this.id = id;
    }
}
