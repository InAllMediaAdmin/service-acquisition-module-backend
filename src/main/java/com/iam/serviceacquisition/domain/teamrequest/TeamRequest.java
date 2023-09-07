package com.iam.serviceacquisition.domain.teamrequest;


import com.iam.serviceacquisition.common.enums.ModalProposalStatus;
import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.Industry;
import com.iam.serviceacquisition.domain.Team;
import com.iam.serviceacquisition.domain.TeamProposal;
import com.iam.serviceacquisition.domain.activity.Activity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class TeamRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "customer_lead_id")
    private CustomerLead customerLead;

    @OneToMany(mappedBy = "teamRequest", cascade = CascadeType.MERGE, orphanRemoval = true)
    @Builder.Default
    private List<DueDate> registerDueDates = new ArrayList<>();

    @OneToMany(mappedBy = "teamRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();

    private LocalDate dueDate;

    private LocalDate requestedDate;

    @Column(name = "talent_agent_id")
    private Long talentAgent;

    @Column(name = "client_partner_id")
    private Long clientPartner;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TeamRequestStatus status = TeamRequestStatus.IN_PROGRESS;

    @Column(name = "modal_proposal_status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ModalProposalStatus modalProposalStatus = ModalProposalStatus.TO_OPEN;

    private String additionalInformation;

    private String shortClientNeedDescription;

    private String longClientNeedDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_proposal_id")
    private TeamProposal proposal;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column(name = "share_count")
    private Integer shareCount = 0;

    public boolean isInProgress() {
        return this.status == TeamRequestStatus.IN_PROGRESS;
    }

    public boolean isCanceled() {
        return this.status == TeamRequestStatus.CANCELED;
    }

    public boolean isBooked() {
        return this.status == TeamRequestStatus.BOOKED;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.registerDueDates.add(DueDate.builder()
                .date(dueDate)
                .status(status)
                .teamRequest(this)
                .build());
    }

    public void addActivity (Activity activity){
        if (activities == null){
            activities = new ArrayList<>();
        }
        if (activity != null){
            activities.add(activity);
            activity.setTeamRequest(this);
        }
    }

    public TeamRequest clone() {
        TeamRequest cloned = TeamRequest.builder()
                .team(team)
                .customerLead(customerLead)
                .dueDate(dueDate)
                .requestedDate(requestedDate)
                .talentAgent(talentAgent)
                .clientPartner(clientPartner)
                .status(status)
                .modalProposalStatus(modalProposalStatus)
                .additionalInformation(additionalInformation)
                .shortClientNeedDescription(shortClientNeedDescription)
                .longClientNeedDescription(longClientNeedDescription)
                .industry(industry)
                .shareCount(0)
                .build();

        for (DueDate dueDate : registerDueDates) {
            cloned.getRegisterDueDates().add(dueDate.clone(cloned));
        }
        cloned.setProposal(proposal.clone(cloned));

        if(shareCount == null) {
            shareCount = 0;
        }
        shareCount += 1;

        return cloned;
    }
}
