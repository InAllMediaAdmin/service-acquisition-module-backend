package com.iam.serviceacquisition.domain;

import com.iam.user.account.common.enums.UserRole;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class UserProfile {

    @Id
    private Long id;

    @Column(name = "has_request", nullable = false)
    @Builder.Default
    private Boolean hasRequest = FALSE;

    @ElementCollection(targetClass= UserRole.class)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();
}
