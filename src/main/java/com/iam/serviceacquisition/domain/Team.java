package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.common.enums.SearchPositionSlotType;
import com.iam.serviceacquisition.common.enums.TalentLevel;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.iam.serviceacquisition.common.enums.SearchPositionSlotType.STANDARD;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TeamPositionSlot> teamPositionSlots = newArrayList();

    @ManyToOne
    @JoinColumn(name = "search_request_id")
    private SearchRequest searchRequest;

    private int matching;

    private BigDecimal rate;

    private LocalDate availabilityDate;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    private String teamName;

    private String teamLogo;

    @OneToMany(mappedBy = "team")
    private List<TeamRequest> requests = new ArrayList<>();

    @Transient
    private Long quantityTalentAdHoc;

    @Transient
    private String country;


    public void addTeamPositionSlot(TeamPositionSlot teamPositionSlot) {
        if (teamPositionSlots.contains(teamPositionSlot)) {
            teamPositionSlot.setTeam(this);
        }
    }

    public void removeTeamPositionSlot(TeamPositionSlot teamPositionSlot) {
        teamPositionSlots.remove(teamPositionSlot);
        teamPositionSlot.setSearchPositionSlot(null);
    }

    public List<Talent> getStandardTalent() {
        return teamPositionSlots.stream().filter(t -> t.getSearchPositionSlot().getType() == STANDARD)
                .map(TeamPositionSlot::getTalent).collect(Collectors.toList());

    }

    public void calculateRate() {
        this.rate = teamPositionSlots.stream()
                .map(TeamPositionSlot::getTalent)
                .map(Talent::getRate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(0, RoundingMode.HALF_DOWN)
                .divide(BigDecimal.valueOf(teamPositionSlots.size()), RoundingMode.HALF_DOWN);
    }

    public void addTalentWithExclusiveSearchPositionSlot(Talent talent) {
        List<Long> technologiesId = talent.getMainTechnologies().stream().map(Technology::getId)
                .collect(Collectors.toList());
        List<SearchPositionSlot> positionSlotByRoleAndMainTech = searchRequest.getPositionsSlots().stream()
                .filter(sps -> talent.getRoles().contains(sps.getDefaultRoleId())
                        && talent.getLevel().getLevel() == sps.getSeniority().getLevel()
                        && technologiesId.stream().anyMatch(tech -> sps.getMainTechnologies().stream()
                            .map(Technology::getId).collect(Collectors.toList()).contains(tech))
                        && !sps.isLinkedToTalentOnSearch()).collect(toList());

        SearchPositionSlot searchPositionSlot = positionSlotByRoleAndMainTech.iterator().next();
        if (positionSlotByRoleAndMainTech.size() > 1) {
            searchPositionSlot = findBestMatchForSecondaryTechnologies(talent, positionSlotByRoleAndMainTech);
        }

        addRolesToMainTechnologies(talent, searchPositionSlot);

        TeamPositionSlot teamPositionSlot = TeamPositionSlot.builder()
                .talent(talent)
                .searchPositionSlot(searchPositionSlot)
                .build();

        teamPositionSlots.add(teamPositionSlot);
    }

    public void addTalents(List<Talent> talents, SearchRequest searchRequest) {
        talents.forEach(t -> addNewTalent(t, SearchPositionSlotType.STANDARD, searchRequest));
    }

    public void addNewTalent(Talent talent, SearchPositionSlotType type, SearchRequest searchRequest) {
        var technologiesFromSearch = searchRequest.getPositionsSlots()
                .stream().map(SearchPositionSlot::getMainTechnologies).filter(Objects::nonNull)
                .flatMap(Collection::stream).collect(toList());
        SearchPositionSlot searchPositionSlot = SearchPositionSlot.builder()
                .mainTechnologies(Optional.ofNullable(talent.getMainTechnologies()).orElse(newArrayList()).stream()
                        .distinct()
                        .filter(technologiesFromSearch::contains)
                        .collect(Collectors.toList()))
                .seniority(talent.getLevel())
                .type(type)
                .defaultRoleId(talent.getRoles().iterator().next())
                .build();

        TeamPositionSlot teamPositionSlot = TeamPositionSlot.builder()
                .talent(talent)
                .searchPositionSlot(searchPositionSlot)
                .build();

        teamPositionSlots.add(teamPositionSlot);
    }

    public void addAvailabilityDate() {
        Optional<Talent> talentOptional = teamPositionSlots.stream().map(TeamPositionSlot::getTalent)
                .max(Comparator.comparing(t -> TalentLevel.fromId(t.getLevel().getId()).getLevel()));
        talentOptional.ifPresent(talent -> {
            if (nonNull(talent.getUpgradeAvailabilityDate())) {
                availabilityDate = talent.getUpgradeAvailabilityDate();
            } else {
                availabilityDate = talent.getAvailabilityDate();
            }
        });
    }

    public void calculateCountry() {
        this.country = teamPositionSlots.stream()
                .map(TeamPositionSlot::getTalent)
                .filter(t -> t.getTimeZone().equals(timeZone))
                .map(Talent::countryTimezone)
                .sorted().findFirst().get();
    }

    public void addTimeZone(Float timeZoneCriteria) {
        Map<TimeZone, Long> map = teamPositionSlots.stream().map(TeamPositionSlot::getTalent)
                .collect(groupingBy(Talent::getTimeZone, counting()));

        map.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(entry -> timeZone =
                map.entrySet().stream()
                        .filter(e -> e.getValue().equals(entry.getValue()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList())
                        .stream()
                        .min(Comparator.comparingDouble(i -> Math.abs(i.getTimeZoneOffset() - timeZoneCriteria)))
                        .orElse(entry.getKey()));
    }

    public void addAdditionalTalent(Talent additionalTalent, SearchRequest searchRequest) {
        addNewTalent(additionalTalent, SearchPositionSlotType.ADDITIONAL, searchRequest);
    }

    public List<Talent> getAdditionalTalents() {
        return this.teamPositionSlots.stream()
                .filter(t -> nonNull(t.getSearchPositionSlot()) && t.getSearchPositionSlot().getType() == SearchPositionSlotType.ADDITIONAL)
                .map(TeamPositionSlot::getTalent).collect(toList());
    }


    public List<Talent> getStandardTalents() {
        return this.teamPositionSlots.stream()
                .filter(t -> nonNull(t.getSearchPositionSlot()) && t.getSearchPositionSlot().getType() == SearchPositionSlotType.STANDARD)
                .map(TeamPositionSlot::getTalent).collect(toList());
    }

    public Set<Industry> getMainIndustries() {
        if (isEmpty(teamPositionSlots)) return Set.of();
       return teamPositionSlots.stream()
               .map(tps -> tps.getTalent().getMainIndustryExperience())
               .filter(Optional::isPresent)
               .map(Optional::get)
               .collect(Collectors.toSet());
    }

    private SearchPositionSlot findBestMatchForSecondaryTechnologies(Talent talent, List<SearchPositionSlot> searchPositionSlots) {
        SearchPositionSlot searchPositionSlot = null;

        for (SearchPositionSlot spl : searchPositionSlots) {
            List<Technology> secondariesTechnologies = talent.getSecondaryTechnologies();

            if (isNotEmpty(spl.getSecondaryTechnologies()) && isNotEmpty(secondariesTechnologies) && secondariesTechnologies.stream()
                    .anyMatch(st -> spl.getSecondaryTechnologies().contains(st))
            ) {
                searchPositionSlot = spl;
                searchPositionSlot.setLinkedToTalentOnSearch(true);
                break;
            }
        }

        if (isNull(searchPositionSlot)) {
            searchPositionSlot = searchPositionSlots.iterator().next();
            searchPositionSlot.setLinkedToTalentOnSearch(true);
        }

        return searchPositionSlot;
    }

    private void addRolesToMainTechnologies(Talent talent, SearchPositionSlot searchPositionSlot) {
        List<Technology> talentTechnologies = talent.getMainTechnologies();

        for (int i = 0; i < searchPositionSlot.getMainTechnologies().size(); i++) {
            Technology technology = searchPositionSlot.getMainTechnologies().get(i);
            if (talentTechnologies.contains(technology)) {
                searchPositionSlot.getMainTechnologies().set(i, talentTechnologies.get(talentTechnologies.indexOf(technology)));
            }
        }
    }

}
