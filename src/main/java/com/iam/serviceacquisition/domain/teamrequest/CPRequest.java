package com.iam.serviceacquisition.domain.teamrequest;

import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.Industry;
import com.iam.serviceacquisition.domain.Technology;
import com.iam.serviceacquisition.domain.TimeZone;
import lombok.*;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client_partner_request")
public class CPRequest {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "client_partner_id")
    private Long clientPartner;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CPRequestStatus status = CPRequestStatus.NEW;

    @Column(name = "comments")
    private String comments;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(
            name = "client_partner_request_technologies",
            joinColumns = @JoinColumn(name = "client_partner_request_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id"))
    private List<Technology> technologies = newArrayList();

    @Column(name = "min_team_composition")
    private Integer minTeamComposition;

    @Column(name = "max_team_composition")
    private Integer maxTeamComposition;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    @Column(name = "blended_hourly_rate")
    private String blendedHourlyRate;

    @ManyToOne
    @JoinColumn(name = "customer_lead_id")
    private CustomerLead customerLead;

    @Column(name = "pre_sales_user_id")
    private Long preSalesUserId;

    @PrePersist
    protected void onCreate() {
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

}
