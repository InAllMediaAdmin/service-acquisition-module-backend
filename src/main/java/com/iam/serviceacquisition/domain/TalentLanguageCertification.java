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
    @GeneratedValue
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
