package com.iam.serviceacquisition.domain.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String body;

    private String attachment;

    @Column(name = "read_by_tm")
    private boolean readByTM;

    @Column(name = "read_by_cp")
    private boolean readByCP;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_id")
    private Activity activity = new Activity();

}
