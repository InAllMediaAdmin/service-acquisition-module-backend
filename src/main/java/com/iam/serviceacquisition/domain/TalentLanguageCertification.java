package com.iam.serviceacquisition.domain;


import lombok.*;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Data
public class TalentLanguageCertification {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    private int year;

    private String institution;

    private String certification;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "talent_id")
    private Talent talent;


    public TalentLanguageCertification clone(Talent talent) {
        return this.toBuilder()
                .id(null)
                .talent(talent)
                .build();
    }


}
