package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.domain.search.SearchFilter;
import lombok.*;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SearchRequest {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "searchRequest", cascade = CascadeType.ALL)
    @Builder.Default
    private List<SearchPositionSlot> positionsSlots = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private SearchFilter filter;

    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    @Column(name = "created")
    private Instant createdDate;

    @Column(name = "user")
    private Long user;

    @OneToMany(mappedBy = "searchRequest", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public void addSearchPositionSlot(SearchPositionSlot searchPositionSlot) {
        positionsSlots.add(searchPositionSlot);
        searchPositionSlot.setSearchRequest(this);
    }

    public List<SearchPositionSlot> findPositionSlotByRole(Long roleId) {
        return positionsSlots.stream().filter(p -> p.getDefaultRoleId().equals(roleId)).collect(toList());
    }
}
