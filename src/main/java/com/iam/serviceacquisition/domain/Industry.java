package com.iam.serviceacquisition.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @Builder @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Industry {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    private String description;
}
