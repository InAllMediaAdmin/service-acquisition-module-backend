package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TeamPositionSlot> teamPositionSlots = newArrayList();

    @ManyToOne
    @JoinColumn(name = "search_request_id")
    private SearchRequest searchRequest;

    private int matching;

    private BigDecimal rate;

    private LocalDate availabilityDate;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    private String teamName;

    private String teamLogo;

    @OneToMany(mappedBy = "team")
    private List<TeamRequest> requests = new ArrayList<>();

    @Transient
    private Long quantityTalentAdHoc;

    @Transient
    private String country;

}
