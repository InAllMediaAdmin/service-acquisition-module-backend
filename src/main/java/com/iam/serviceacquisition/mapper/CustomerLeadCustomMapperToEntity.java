package com.iam.serviceacquisition.mapper;

import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.dto.CustomerLeadDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.service.CPRequestService;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class CustomerLeadCustomMapperToEntity extends AbstractConverter<CustomerLeadDTO, CustomerLead> {

    private final CPRequestService cpRequestService;

    @Autowired
    public CustomerLeadCustomMapperToEntity(final CPRequestService cpRequestService) {
        this.cpRequestService = cpRequestService;
    }

    @Override
    protected CustomerLead convert(CustomerLeadDTO customerLeadDTO) {
        ModelMapper modelMapper = new ModelMapper();
        CustomerLead customerLead = modelMapper.map(customerLeadDTO, CustomerLead.class);
        if (isNotEmpty(customerLeadDTO.getCpRequestIdList())) {
            customerLeadDTO.getCpRequestIdList().forEach(cpRequestId -> {
                Optional<CPRequest> cpRequest = cpRequestService.findById(cpRequestId);
                cpRequest.ifPresent(request -> customerLead.getCpRequests().add(request));
            });
        }
        return customerLead;
    }

}
