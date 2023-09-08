package com.iam.serviceacquisition.domain.talentresume;

import com.iam.serviceacquisition.domain.Strategy;
import com.iam.serviceacquisition.domain.Talent;
import lombok.*;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@EqualsAndHashCode
public class TalentSoftSkill {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @Column(name = "description")
    private String description;

    @Column(name = "name_person")
    private String namePerson;

    @Column(name = "role_person")
    private String rolePerson;

    @ManyToOne
    @JoinColumn(name = "talent_experience_id")
    private TalentExperience talentExperience;

    public TalentSoftSkill clone(Talent talent){
        return this.toBuilder()
                .id(null)
                .talent(talent)
                .build();
    }
}
