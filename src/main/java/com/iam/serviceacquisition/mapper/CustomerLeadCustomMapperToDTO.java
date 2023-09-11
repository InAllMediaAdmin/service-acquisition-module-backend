package com.iam.serviceacquisition.mapper;

import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.Technology;
import com.iam.serviceacquisition.domain.dto.CustomerLeadDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class CustomerLeadCustomMapperToDTO extends AbstractConverter<CustomerLead, CustomerLeadDTO> {

    @Override
    public CustomerLeadDTO convert(CustomerLead customerLead) {
        ModelMapper modelMapper = new ModelMapper();
        CustomerLeadDTO customerLeadDTO = modelMapper.map(customerLead, CustomerLeadDTO.class);

        if (isNotEmpty(customerLead.getTeamRequests())) {
            Optional<TeamRequest> lastTeamRequest = customerLead.getTeamRequests().stream()
                    .max(Comparator.comparing(TeamRequest::getRequestedDate)
                            .thenComparing(TeamRequest::getId));
            lastTeamRequest.ifPresent(tr -> {
                customerLeadDTO.setStatusLastTeamRequest(tr.getStatus());

                List<Technology> mainTechnologies = tr.getTeam().getTeamPositionSlots().stream()
                        .flatMap(tps -> tps.getTalent().getMainTechnologies().stream())
                        .collect(Collectors.toList());
                customerLeadDTO.setMainTechnologiesLastTeamRequest(mainTechnologies);
                if (nonNull(tr.getProposal())) {
                    customerLeadDTO.setBlendedRateLastTeamRequest(tr.getProposal().getBlendedRate());
                }
                customerLeadDTO.setRequestedDateLastTeamRequest(tr.getRequestedDate());
                customerLeadDTO.setMembersSizeLastTeamRequest((nonNull(tr.getTeam())) ? tr.getTeam().getTeamPositionSlots().size() : 0);

            });
        }
        if (isNotEmpty(customerLead.getCpRequests())) {
            customerLeadDTO.setCpRequestIdList(customerLead.getCpRequests().stream()
                    .map(CPRequest::getId)
                    .collect(Collectors.toList()));
        }

        return customerLeadDTO;
    }
}
