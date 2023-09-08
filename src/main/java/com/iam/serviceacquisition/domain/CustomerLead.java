package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import lombok.*;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class CustomerLead {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @Column(name = "lead_project_name")
    private String leadProjectName;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @OneToMany(mappedBy = "customerLead", fetch = FetchType.LAZY)
    private List<TeamRequest> teamRequests;

    @OneToMany(mappedBy = "customerLead", fetch = FetchType.LAZY)
    private List<CPRequest> cpRequests = new ArrayList<>();

    @Column(name = "client_partner_id")
    private Long clientPartner;

    @ManyToOne
    @JoinColumn(name = "stakeholder_id")
    private Stakeholder stakeholder;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "created")
    private Instant created;
}
