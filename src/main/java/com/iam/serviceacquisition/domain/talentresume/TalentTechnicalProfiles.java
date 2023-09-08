package com.iam.serviceacquisition.domain.talentresume;

import com.iam.serviceacquisition.common.enums.TalentLevel;
import com.iam.serviceacquisition.domain.Talent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class TalentTechnicalProfiles {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "default_role_id", nullable = false)
    private Long defaultRoleId;

    @Column(name = "seniority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TalentLevel level;

    @Column(name = "current_profile")
    private boolean currentProfile;

}
