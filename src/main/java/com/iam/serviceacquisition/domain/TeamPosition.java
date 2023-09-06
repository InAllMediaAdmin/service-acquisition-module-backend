package com.iam.serviceacquisition.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TeamPosition {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Column(name ="positionIndex")
    private long index;
}
