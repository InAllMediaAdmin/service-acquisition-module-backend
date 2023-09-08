package com.iam.serviceacquisition.domain;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Company {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "client_id")
    private Long clientId;
}
