package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.domain.search.dto.SearchTalentFilterDTO;
import com.iam.user.account.common.model.TalentRoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchRequestDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonProperty("position_slots")
    private List<SearchPositionSlotDTO> positionSlot = newArrayList();

    @JsonProperty("start_date")
    @NotNull
    @ApiModelProperty(required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty("time_zone")
    @NotNull
    @ApiModelProperty(required = true)
    private TimeZoneDTO timeZone;

    @JsonProperty("talent_filter")
    private SearchTalentFilterDTO filter;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @JsonProperty("industry_id")
    private Long industryId;

    @ApiModelProperty(hidden = true)
    public long calculateQuantityByRole(Long roleId) {
        return positionSlot.stream().filter(p -> p.getRole().getId().equals(roleId)).count();
    }

    @ApiModelProperty(hidden = true)
    public List<Long> findRolesIdsListed() {
        return positionSlot.stream().map(
                SearchPositionSlotDTO::getRole).map(TalentRoleDTO::getId).collect(toList());
    }

    public static SearchRequestDTO from(SearchRequestDTO searchRequestDTO) {
        return SearchRequestDTO.builder()
                .timeZone(searchRequestDTO.getTimeZone())
                .startDate(searchRequestDTO.getStartDate())
                .positionSlot(Collections.emptyList())
                .build();
    }
}
