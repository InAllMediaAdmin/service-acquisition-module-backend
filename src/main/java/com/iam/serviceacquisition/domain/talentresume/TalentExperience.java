package com.iam.serviceacquisition.domain.talentresume;

import com.iam.serviceacquisition.domain.Industry;
import com.iam.serviceacquisition.domain.Talent;
import com.iam.serviceacquisition.domain.Technology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.EMPTY_LIST;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
public class TalentExperience {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String role;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "main_technology")
    private Technology mainTechnology;

    @Column(name = "main_technology_years")
    private Double mainTechnologyYears;

    @ManyToMany
    private List<Technology> additionalTechnologies  = new ArrayList<>();

    private String company;

    private String description;

    @Transient
    private String description_plain;

    private String activities;

    @Transient
    private String activities_plain;

    @Column(name = "iam_experience")
    private Boolean iamExperience;

    @Column(name = "remote_work")
    private Boolean remoteWork;

    @Transient
    private String assignedTo;

    @Column(name = "currently")
    private Boolean currently;

    @OneToMany(mappedBy = "talentExperience")
    @Builder.Default
    private List<TalentSoftSkill> talentSoftSkills = new ArrayList<>();

    public void addAdditionalTechnology(Technology technology) {
        this.additionalTechnologies.add(technology);
    }

    public void calculateMainTechnologyYearsExperience() {
       this.mainTechnologyYears =  this.getExperience();
    }

    public double getExperience() {
        LocalDate endDate = this.endDate;
        LocalDate now = LocalDate.now();

        if (endDate == null || endDate.isAfter(now)) {
            endDate = now;
        }
        double diff = ChronoUnit.MONTHS.between(startDate, endDate) / 12d;
        return Math.floor(diff * 100) / 100;
    }


    /**
     * Implemented custom equals so that two talent experiences with null IDs are considered
     * different. This was done so that we can add several transient experiences (which have null IDs)
     * to the owning Talent's Set and they can all be persisted at the same time, instead of each new one
     * replacing the previous one.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TalentExperience))
            return false;
        TalentExperience other = (TalentExperience)o;
        boolean idEquals = this.id != null && other.id != null && this.id.equals(other.id);
        return idEquals;
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (id != null) {
            result = 31 * result + id.hashCode();
        }
        return result;
    }

    public TalentExperience clone(Talent talent) {
        return this.toBuilder()
                .id(null)
                .talent(talent)
                .additionalTechnologies((isEmpty(additionalTechnologies) ? EMPTY_LIST : newArrayList(additionalTechnologies)))
                .talentSoftSkills(null)
                .build();
    }
}
