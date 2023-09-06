package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.domain.search.SearchFilter;
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
public class ComboStructure {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL)
    @Builder.Default
    private List<SearchPositionSlot> positionsSlots = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private SearchFilter filter;

}
