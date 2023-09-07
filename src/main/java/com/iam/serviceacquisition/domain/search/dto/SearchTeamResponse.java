package com.iam.serviceacquisition.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.SearchPositionSlotType;
import com.iam.serviceacquisition.highlights.domain.HighLightDTO;
import com.iam.serviceacquisition.domain.dto.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;


@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class SearchTeamResponse {

    private Long id;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_logo")
    private String teamLogo;

    @JsonProperty("matching_team")
    private Integer matching;

    @JsonInclude(NON_NULL)
    @JsonProperty("avg_rate_team")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal rate;

    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("availability_date_team")
    private LocalDate availabilityDate;

    @JsonProperty("time_zone_team")
    private TimeZoneDTO timeZone;

    @JsonProperty("highlights")
    private HighLightDTO highlights;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long quantityTalentAdHoc;

    private final List<TeamPositionSlotDTO> teamPositionSlots = newArrayList();

    @JsonProperty("industry")
    private IndustryDTO industry;

    @JsonProperty("search_request")
    private SearchRequestDTO searchRequest;

    @JsonProperty("team_country")
    private String country;

    public void addNewTalent(TalentDTO talentDTO, SearchPositionSlotType type, SearchRequestDTO searchRequestDTO) {
        var technologiesFromSearch = searchRequestDTO.getPositionSlot()
                .stream().map(SearchPositionSlotDTO::getMainTechnologies).filter(Objects::nonNull)
                .flatMap(Collection::stream).collect(toList());
        SearchPositionSlotDTO searchPositionSlotDTO = SearchPositionSlotDTO.builder()
                .mainTechnologies(Optional.ofNullable(talentDTO.getMainTechnologies()).orElse(newArrayList()).stream()
                        .distinct()
                        .filter(technologiesFromSearch::contains)
                        .collect(Collectors.toList()))
                .seniority(talentDTO.getLevel())
                .type(type)
                .build();

        TeamPositionSlotDTO teamPositionSlotDTO = TeamPositionSlotDTO.builder()
                .talent(talentDTO)
                .searchPositionSlot(searchPositionSlotDTO)
                .build();

        teamPositionSlots.add(teamPositionSlotDTO);
    }

    public void calculateRate(){
        this.rate = teamPositionSlots.stream()
                .map(TeamPositionSlotDTO::getTalent)
                .map(TalentDTO::getRate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(0, RoundingMode.HALF_DOWN)
                .divide(BigDecimal.valueOf(teamPositionSlots.size()), RoundingMode.HALF_DOWN);
    }

    // TODO these method were created for test invocation, I'd rather get rid of them as soon as possible

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(hidden = true)
    public List<TalentDTO> getAdditionalTalents() {
        return this.teamPositionSlots.stream()
                .filter(t -> t.getSearchPositionSlot().getType().getId() == SearchPositionSlotType.ADDITIONAL.getId())
                .map(TeamPositionSlotDTO::getTalent).collect(toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(hidden = true)
    public List<TalentDTO> getStandardTalents() {
        return this.teamPositionSlots.stream()
                .filter(t -> t.getSearchPositionSlot().getType().getId() == SearchPositionSlotType.STANDARD.getId())
                .map(TeamPositionSlotDTO::getTalent).collect(toList());
    }
}
