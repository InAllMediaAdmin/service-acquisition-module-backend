package com.iam.serviceacquisition.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Data
@Builder
public class CountryDTO {

    private Integer id;

    private String name;

}
