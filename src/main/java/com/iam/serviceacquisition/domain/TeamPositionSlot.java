package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.domain.activity.Activity;
import com.iam.serviceacquisition.domain.talentresume.TalentResume;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TeamPositionSlot {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "position_slot_id")
    @EqualsAndHashCode.Exclude
    private SearchPositionSlot searchPositionSlot;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @EqualsAndHashCode.Exclude
    private Team team;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @OneToOne
    @JoinColumn(name="talent_resume_id")
    @EqualsAndHashCode.Exclude
    private TalentResume talentResume;

    @OneToMany(mappedBy = "teamPositionSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();

    public void addActivity (Activity activity){
        if (activities == null){
            activities = new ArrayList<>();
        }
        if (activity != null){
            activities.add(activity);
            activity.setTeamPositionSlot(this);
        }
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
        activity.setTeamPositionSlot(null);
    }
}
