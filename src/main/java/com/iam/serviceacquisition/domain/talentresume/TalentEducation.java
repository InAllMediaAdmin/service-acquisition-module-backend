package com.iam.serviceacquisition.domain.talentresume;

import com.iam.serviceacquisition.domain.Talent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class TalentEducation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String school;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private TalentDegreeEducation degree;

    public TalentEducation clone(Talent talent){
        return this.toBuilder()
                .id(null)
                .talent(talent)
                .build();
    }
}