package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TeamDTO {

    private Long id;

    @JsonProperty("team_position_slots")
    private List<TeamPositionSlotDTO> teamPositionSlots;

    @JsonProperty("search_request")
    private SearchRequestDTO searchRequest;

    @JsonInclude(NON_NULL)
    private BigDecimal rate;

    @JsonProperty("start_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate availabilityDate;

    @JsonProperty("time_zone")
    private TimeZoneDTO timeZone;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_logo")
    private String teamLogo;
}
