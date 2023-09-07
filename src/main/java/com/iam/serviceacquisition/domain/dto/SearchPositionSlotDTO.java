package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.SearchPositionSlotType;
import com.iam.user.account.common.model.TalentRoleDTO;
import lombok.*;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter @Setter
@Builder
public class SearchPositionSlotDTO {

    @JsonProperty("role")
    private TalentRoleDTO role;

    @JsonProperty("seniority")
    private LevelDTO seniority;

    @JsonProperty("main_technologies")
    private List<TechnologyDTO> mainTechnologies;

    @JsonProperty("secondary_technology")
    private List<TechnologyDTO> secondaryTechnologies;

    @JsonProperty("type")
    private SearchPositionSlotType type;

    @JsonProperty(value = "matching", access = JsonProperty.Access.READ_ONLY)
    private int matching;

    public boolean matchTalentRequirements(TalentDTO talentDTO) {
        List<Long> talentsIds = this.mainTechnologies.stream().map(TechnologyDTO::getId).collect(toList());
        return  talentsIds.stream().anyMatch(id ->
                    talentDTO.getMainTechnologies().stream().map(TechnologyDTO::getId).collect(toList()).contains(id))
                && this.seniority.getId() == talentDTO.getLevel().getId();
    }

    public static SearchPositionSlotDTO from(TalentDTO talentDTO) {
        return SearchPositionSlotDTO.builder()
                .seniority(talentDTO.getLevel())
                .mainTechnologies(talentDTO.getMainTechnologies())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPositionSlotDTO that = (SearchPositionSlotDTO) o;
        return Objects.equals(seniority.getId(), that.seniority.getId())
                && mainTechnologyEquals(mainTechnologies, that.mainTechnologies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seniority.getId(), mainTechnologies);
    }

    private boolean mainTechnologyEquals(List<TechnologyDTO> l1, List<TechnologyDTO> l2){
        if (l1 == null && l2 == null) return true;
        if (l1 == null || l2 == null) return false;
        List<Long> techs1 = l1.stream().map(TechnologyDTO::getId).collect(toList());
        List<Long> techs2 = l2.stream().map(TechnologyDTO::getId).collect(toList());
        return techs1.size() == techs2.size() && techs1.containsAll(techs2) && techs2.containsAll(techs1);
    }
}
