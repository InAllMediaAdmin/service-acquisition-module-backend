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
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-generator")
    @TableGenerator(name="table-generator", table="hibernate_sequence",
            pkColumnName="sequence_name", valueColumnName="next_not_cached_value",
            pkColumnValue="acquisition_seq", allocationSize=1)
    private Long id;

    @Column(name = "has_request", nullable = false)
    @Builder.Default
    private Boolean hasRequest = FALSE;

    @ElementCollection(targetClass= UserRole.class)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();
}
