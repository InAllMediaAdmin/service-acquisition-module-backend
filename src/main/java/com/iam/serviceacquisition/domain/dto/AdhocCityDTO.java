package com.iam.serviceacquisition.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AdhocCityDTO {

    private Long id;

    private String city;

    private TimeZoneDTO timeZone;
}
