package com.iam.serviceacquisition.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TimeZone {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
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
