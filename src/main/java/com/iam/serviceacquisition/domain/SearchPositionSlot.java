package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.common.enums.SearchPositionSlotType;
import com.iam.serviceacquisition.common.enums.TalentLevel;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SearchPositionSlot {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "default_role_id")
    private Long defaultRoleId;

    @Column(name = "seniority")
    @Enumerated(EnumType.STRING)
    private TalentLevel seniority;

    @ManyToMany
    private List<Technology> mainTechnologies;

    @ManyToMany
    private List<Technology> secondaryTechnologies;

    @Column(name = "position_slot_type")
    @Enumerated(EnumType.STRING)
    private SearchPositionSlotType type;

    private int matching;

    @ManyToOne
    @JoinColumn(name = "search_request_id")
    private SearchRequest searchRequest;

    @ManyToOne
    @JoinColumn(name = "combo_id")
    private ComboStructure combo;

    @OneToMany(mappedBy = "searchPositionSlot", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TeamPositionSlot> teamPositionSlots = new ArrayList<>();

    @Transient
    private boolean chosenBySecondaryMatching;

    @Transient
    public boolean linkedToTalentOnSearch;

    public SearchPositionSlot removeTeamPositionSlot(TeamPositionSlot teamPositionSlot) {
        teamPositionSlots.remove(teamPositionSlot);
        teamPositionSlot.setSearchPositionSlot(null);
        return this;
    }

    public boolean matchTalentRequirementsByRole(Talent talent) {
        return talent.getRoles().stream().anyMatch(r -> this.defaultRoleId.equals(r))
                && this.mainTechnologies.stream().map(Technology::getId)
                .anyMatch(t -> talent.getMainTechnologies().stream().map(Technology::getId).collect(toList()).contains(t))
                && this.seniority.getId() == talent.getLevel().getId();

    }

    public boolean matchTalentRequirements(Talent talent) {
        List<Long> talentsIds = this.mainTechnologies.stream().map(Technology::getId).collect(toList());
        return talentsIds.stream().anyMatch(id ->
                talent.getMainTechnologies().stream().map(Technology::getId).collect(toList()).contains(id))
                && this.seniority.getId() == talent.getLevel().getId();
    }

    public static SearchPositionSlot from(Talent talent) {
        return SearchPositionSlot.builder()
                .seniority(talent.getLevel())
                .mainTechnologies(talent.getMainTechnologies().stream()
                        .map(tech -> Technology.builder().id(tech.getId()).build()).collect(toList()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPositionSlot that = (SearchPositionSlot) o;
        return Objects.equals(seniority.getId(), that.seniority.getId())
                && technologiesEquals(mainTechnologies, that.mainTechnologies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seniority.getId(), mainTechnologies);
    }

    public boolean equalsWithSecondaryTechnology(SearchPositionSlot compared) {
        if (this == compared) return true;

        return Objects.equals(seniority.getId(), compared.seniority.getId())
                && Objects.equals(this.role, compared.role)
                && technologiesEquals(mainTechnologies, compared.mainTechnologies)
                && technologiesEquals(secondaryTechnologies, compared.getSecondaryTechnologies());

    }

    private boolean technologiesEquals(List<Technology> l1, List<Technology> l2) {
        if (l1 == null && l2 == null) return true;
        if (l1 == null || l2 == null) return false;
        List<Long> techs1 = l1.stream().map(Technology::getId).collect(toList());
        List<Long> techs2 = l2.stream().map(Technology::getId).collect(toList());
        return techs1.size() == techs2.size() && techs1.containsAll(techs2) && techs2.containsAll(techs1);
    }
}
