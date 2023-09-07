package com.iam.serviceacquisition.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FinancialInformationDTO {
    //TODO: refactor to avoid having this class here and in the service center
    //TODO: snake case for the json!


    private Long id;

    @JsonProperty("talent_id")
    private Long talentId;

    private String beneficiaryAccountName;

    private String beneficiaryAccountEmail;

    private String currency;

    private String beneficiaryAccountNumber;

    private String beneficiaryBankSwift;

    private String beneficiaryBankAba;

    private String beneficiaryAccountType;

    private String beneficiaryAddress;

    private String beneficiaryCity;

    private String beneficiaryCountry;

    private String paymentType;
}
