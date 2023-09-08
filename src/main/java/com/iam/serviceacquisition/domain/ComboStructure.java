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
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL)
    @Builder.Default
    private List<SearchPositionSlot> positionsSlots = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private SearchFilter filter;

}
