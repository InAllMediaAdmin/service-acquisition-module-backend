package com.iam.serviceacquisition.domain;



import com.iam.serviceacquisition.common.util.UUIDUtils;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TeamProposal {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "created")
    private Instant created;

    @Column(name = "blended_rate", precision = 5, scale = 2)
    private BigDecimal blendedRate;

    @Column(name = "team_overview")
    private String teamOverview;

    @Column(name = "proposal_uuid")
    private String uuid;

    @Column(name = "last_update")
    private Instant lastUpdate;

    private LocalDate availabilityDate;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    @OneToOne(mappedBy = "proposal")
    private TeamRequest teamRequest;

    public TeamProposal clone(TeamRequest teamRequest) {
        return TeamProposal.builder()
                .created(created)
                .blendedRate(blendedRate)
                .teamOverview(teamOverview)
                .lastUpdate(lastUpdate)
                .availabilityDate(availabilityDate)
                .timeZone(timeZone)
                .teamRequest(teamRequest)
                .uuid(UUIDUtils.randomUUID())
                .build();
    }
}
