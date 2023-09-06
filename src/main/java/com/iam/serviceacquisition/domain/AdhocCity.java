package com.iam.serviceacquisition.domain;

import lombok.*;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@EqualsAndHashCode
public class AdhocCity {

    @Id
    @GeneratedValue
    private Long id;

    private String city;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;
}
