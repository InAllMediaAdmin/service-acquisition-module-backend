package com.iam.serviceacquisition.config;

import com.iam.serviceacquisition.mapper.CustomerLeadCustomMapperToDTO;
import com.iam.serviceacquisition.mapper.CustomerLeadCustomMapperToEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

//    private final CustomerLeadCustomMapperToEntity customerLeadCustomMapperToEntity;
//    private final CustomerLeadCustomMapperToDTO customerLeadCustomMapperToDTO;
//
//    public ModelMapperConfig(final CustomerLeadCustomMapperToEntity customerLeadCustomMapperToEntity,
//                             final CustomerLeadCustomMapperToDTO customerLeadCustomMapperToDTO){
//        this.customerLeadCustomMapperToEntity = customerLeadCustomMapperToEntity;
//        this.customerLeadCustomMapperToDTO = customerLeadCustomMapperToDTO;
//    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //modelMapper.addConverter(customerLeadCustomMapperToEntity);
        //modelMapper.addConverter(customerLeadCustomMapperToDTO);

        return modelMapper;
    }

}
