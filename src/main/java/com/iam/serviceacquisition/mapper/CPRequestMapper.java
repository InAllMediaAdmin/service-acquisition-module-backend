package com.iam.serviceacquisition.mapper;

import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.activity.Comment;
import com.iam.serviceacquisition.domain.dto.CommentDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.dto.CPRequestDTO;
import com.iam.serviceacquisition.domain.teamrequest.dto.CPRequestStatusDTO;
import com.iam.serviceacquisition.service.CommentService;
import com.iam.serviceacquisition.service.CustomerLeadService;
import com.iam.serviceacquisition.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class CPRequestMapper {

    private final ModelMapper modelMapper;
    private final UserAccountService userAccountService;
    private final CustomerLeadService customerLeadService;
    private final CommentService commentService;

    public <S, D> D mapTo(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    /** dto to entity */
    public CPRequest mapToCPRequestEntity(CPRequestDTO dto) {
        var entity = mapTo(dto, CPRequest.class);
        try {
            entity.setStatus(CPRequestStatus.fromId(dto.getStatusDTO().getId()));
        } catch (Exception e){
            log.warn(e.getMessage());
        }
        if (nonNull(dto.getCustomerLeadId())){
            Optional<CustomerLead> customerLead = customerLeadService.findById(dto.getCustomerLeadId());
            customerLead.ifPresent(entity::setCustomerLead);
        }
        return entity;
    }

    /** entity to dto */
    public CPRequestDTO mapToCPRequestDTO(CPRequest entity) {
        var dto = mapTo(entity, CPRequestDTO.class);
        if (entity.getClientPartner() != null) {
            try {
                dto.setUserDTO(userAccountService.findUserById(entity.getClientPartner()));
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        if (entity.getPreSalesUserId() != null) {
            try {
                dto.setPreSalesUser(userAccountService.findUserById(entity.getPreSalesUserId()));
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        if (entity.getStatus() != null){
            dto.setStatusDTO(CPRequestStatusDTO.builder()
                    .id(entity.getStatus().getId())
                    .description(entity.getStatus().getLabel())
                    .build());
        }
        if (nonNull(entity.getCustomerLead())){
            dto.setCustomerLeadId(entity.getCustomerLead().getId());
            dto.setLeadProjectName(entity.getCustomerLead().getLeadProjectName());
            dto.setCompanyName(entity.getCustomerLead().getCompany().getName());
            dto.setStakeHolderName(entity.getCustomerLead().getStakeholder().getFullName());
            dto.setCustomerLeadLocation(entity.getCustomerLead().getLocation());
        }

        Optional<Comment> commentOptional = commentService.findTopByActivityCpRequestIdOrderByIdDesc(entity.getId());
        commentOptional.ifPresent(comment -> dto.setLastComment(CommentDTO.builder()
                .body(comment.getBody())
                .activityAuthor(userAccountService.findUserById(comment.getActivity().getAuthor()))
                .activityCreated(comment.getActivity().getCreated())
                .build()));
        return dto;
    }
}
