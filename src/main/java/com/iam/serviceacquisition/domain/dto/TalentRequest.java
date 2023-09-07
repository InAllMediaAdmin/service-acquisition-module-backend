package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
public class TalentRequest {

    @NotNull
    @Valid
    @JsonProperty("talents")
    private final List<TalentDTO> talents = new ArrayList<>();
}
