package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iam.serviceacquisition.common.enums.Avatar;
import com.iam.serviceacquisition.domain.Talent;
import com.iam.user.account.common.model.TalentRoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class TalentServiceAdminDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonProperty("avatar")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private Avatar avatar;

    @JsonProperty("firstName")
    @NotBlank
    @ApiModelProperty(required = true)
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank
    @ApiModelProperty(required = true)
    private String lastName;

    @JsonProperty("defaultRole")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private TalentRoleDTO defaultRole;

    @JsonProperty("seniority")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private LevelDTO level;

    @JsonProperty("timezoneId")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private TimeZoneDTO timezone;

    @JsonProperty("email")
    @Email(regexp = "^$|.+@.+\\..+", message = "please provide a valid email address")
    @ApiModelProperty(required = true)
    private String email;

    @JsonProperty("personalEmail")
    @Email(regexp = "^$|.+@.+\\..+", message = "please provide a valid email address")
    @ApiModelProperty(required = true)
    private String personalEmail;

    @JsonProperty("phone_number")
    @NotNull
    @Valid
    @ApiModelProperty(required = true)
    private PhoneDTO phone;

    @JsonProperty("created_in_service_manager")
    private boolean createdInServiceManager;


    public static TalentServiceAdminDTO createTalentServiceAdminDTO(Talent talent, TalentRoleDTO talentRoleDTO) {
        TalentServiceAdminDTO talentServiceAdminDTO = TalentServiceAdminDTO.builder()
                .avatar(talent.getAvatar())
                .firstName(talent.getName())
                .lastName(talent.getLastName())
                .defaultRole(talentRoleDTO)
                .level(LevelDTO.builder()
                        .id(talent.getLevel().getId())
                        .description(talent.getLevel().getLabel())
                        .build())
                .timezone(TimeZoneDTO.builder()
                        .id(talent.getTimeZone().getId())
                        .build())
                .email(talent.getEmailIam())
                .personalEmail(talent.getEmail())
                .phone(PhoneDTO.builder()
                        .countryCode(talent.getPhone().getCountryCode())
                        .areaCode(talent.getPhone().getAreaCode())
                        .phoneNumber(talent.getPhone().getPhoneNumber())
                        .build())
                .id(talent.getServiceCenterId())
                .build();

        validateTalentServiceAdminDTO(talentServiceAdminDTO);
        return talentServiceAdminDTO;

    }

    private static void validateTalentServiceAdminDTO(TalentServiceAdminDTO dto) {
        if (dto.getAvatar() == null
                || dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()
                || dto.getLastName() == null || dto.getLastName().trim().isEmpty()
                || dto.getDefaultRole() == null
                || dto.getLevel() == null
                || dto.getTimezone() == null
                || dto.getEmail() == null || dto.getEmail().trim().isEmpty()
                || dto.getPersonalEmail() == null || dto.getPersonalEmail().trim().isEmpty()
                || dto.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "One or more required fields are null or empty in the TalentServiceAdminDTO");
        }
    }


}
