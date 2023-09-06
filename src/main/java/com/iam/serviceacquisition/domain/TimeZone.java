package com.iam.serviceacquisition.domain;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TimeZone {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "time_zone_offset")
    private float timeZoneOffset;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "medium_description")
    private String mediumDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "country")
    private String country;

    public TimeZone(Long i, float v) {
        this.id = i;
        this.timeZoneOffset = v;
    }

    public TimeZone(float v) {
    }
}
