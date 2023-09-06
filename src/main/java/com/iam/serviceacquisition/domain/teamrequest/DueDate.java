package com.iam.serviceacquisition.domain.teamrequest;

import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DueDate {


    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TeamRequestStatus status;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "team_request_id")
    private TeamRequest teamRequest;

    public DueDate clone(TeamRequest teamRequest) {
        return DueDate.builder()
                .date(date)
                .status(status)
                .teamRequest(teamRequest)
                .build();
    }
}