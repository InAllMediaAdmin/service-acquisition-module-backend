package com.iam.serviceacquisition.domain.activity;

import com.iam.serviceacquisition.domain.Talent;
import com.iam.serviceacquisition.domain.TeamPositionSlot;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    private Long talentId;

    @ManyToOne
    @JoinColumn(name = "team_request_id")
    private TeamRequest teamRequest;

    @ManyToOne
    @JoinColumn(name = "team_position_slot_id")
    private TeamPositionSlot teamPositionSlot;

    @Column(name = "author_id")
    private Long author;

    private Instant created;

    @ManyToOne
    @JoinColumn(name = "client_partner_request_id")
    private CPRequest cpRequest;

    @OneToOne(mappedBy = "activity", cascade = CascadeType.REMOVE)
    private Comment comment;

}
