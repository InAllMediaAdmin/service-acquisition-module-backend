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
public class Stakeholder {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @Column(name = "role")
    private String role;

    @Column(name = "client_id")
    private Long clientId;

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

}
