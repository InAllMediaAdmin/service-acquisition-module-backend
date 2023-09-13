package com.iam.serviceacquisition.mapper;

import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.Technology;
import com.iam.serviceacquisition.domain.dto.CustomerLeadDTO;
import com.iam.serviceacquisition.domain.dto.TechnologyDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import com.iam.serviceacquisition.service.CPRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class CustomerLeadMapper {


    private final ModelMapper modelMapper;
    private final CPRequestService cpRequestService;

    public <S, D> D mapTo(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    /** dto to entity */
    public CustomerLead mapToCustomerLeadEntity(CustomerLeadDTO dto) {
        var entity = mapTo(dto, CustomerLead.class);
        if (isNotEmpty(dto.getCpRequestIdList())) {
            dto.getCpRequestIdList().forEach(cpRequestId -> {
                Optional<CPRequest> cpRequest = cpRequestService.findById(cpRequestId);
                cpRequest.ifPresent(request -> entity.getCpRequests().add(request));
            });
        }
        return entity;
    }

    /** entity to dto */
    public CustomerLeadDTO mapToCustomerLeadDTO(CustomerLead entity) {
        var dto = mapTo(entity, CustomerLeadDTO.class);
        if (isNotEmpty(entity.getTeamRequests())) {
            Optional<TeamRequest> lastTeamRequest = entity.getTeamRequests().stream()
                    .max(Comparator.comparing(TeamRequest::getRequestedDate)
                            .thenComparing(TeamRequest::getId));
            lastTeamRequest.ifPresent(tr -> {
                dto.setStatusLastTeamRequest(tr.getStatus());

                List<TechnologyDTO> mainTechnologies = tr.getTeam().getTeamPositionSlots().stream()
                        .flatMap(tps -> tps.getTalent().getMainTechnologies().stream())
                        .collect(Collectors.toList());
                dto.setMainTechnologiesLastTeamRequest(mainTechnologies);
                if (nonNull(tr.getProposal())) {
                    dto.setBlendedRateLastTeamRequest(tr.getProposal().getBlendedRate());
                }
                dto.setRequestedDateLastTeamRequest(tr.getRequestedDate());
                dto.setMembersSizeLastTeamRequest((nonNull(tr.getTeam())) ? tr.getTeam().getTeamPositionSlots().size() : 0);

            });
        }
        if (isNotEmpty(entity.getCpRequests())) {
            dto.setCpRequestIdList(entity.getCpRequests().stream()
                    .map(CPRequest::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
