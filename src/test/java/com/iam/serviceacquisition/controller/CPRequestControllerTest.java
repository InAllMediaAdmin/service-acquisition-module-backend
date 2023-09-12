package com.iam.serviceacquisition.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.domain.Industry;
import com.iam.serviceacquisition.domain.dto.IndustryDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.dto.CPRequestDTO;
import com.iam.serviceacquisition.domain.teamrequest.dto.CPRequestStatusDTO;
import com.iam.serviceacquisition.mapper.CPRequestMapper;
import com.iam.serviceacquisition.service.CPRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters =  false)
@ActiveProfiles("test")
public class CPRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CPRequestService cpRequestService;

    @MockBean
    private CPRequestMapper mapper;

    @Test
    public void testCreateOrUpdate() throws Exception {
        IndustryDTO industryDTO = IndustryDTO.builder()
                .id(1L)
                .build();

        CPRequestStatusDTO statusDTO = CPRequestStatusDTO.builder()
                .id(1L)
                .build();

        CPRequestDTO cpRequestDTO = CPRequestDTO.builder()
                .id(1L)
                .title("Test Title")
                .clientPartner(1L)
                .industry(industryDTO)
                .statusDTO(statusDTO)
                .comments("Test Comments")
                .build();

        Industry industry = Industry.builder()
                .id(1L)
                .build();
        CPRequest cpRequest = CPRequest.builder()
                .id(1L)
                .title("Test Title")
                .clientPartner(1L)
                .industry(industry)
                .status(CPRequestStatus.NEW)
                .comments("Test Comments")
                .updatedAt(Instant.now())
                .build();

        given(mapper.mapToCPRequestEntity(cpRequestDTO)).willReturn(cpRequest);
        given(cpRequestService.create(cpRequest)).willReturn(cpRequest);
        given(mapper.mapToCPRequestDTO(cpRequest)).willReturn(cpRequestDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(cpRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/cp-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllCPRequest() throws Exception {

        // Define your parameters
        int page = 0;
        int size = 10;
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "updatedAt"));
        Sort sort = Sort.by(orders);

        Pageable pageable = PageRequest.of(page, size, sort);

        // Assume you have an instance of CPRequest
        CPRequest cpRequest = CPRequest.builder()
                .id(1L)
                .title("Test CPRequest")
                .clientPartner(1L)
                .industry(new Industry())
                .status(CPRequestStatus.NEW)
                .comments("Test comments")
                .updatedAt(Instant.now())
                .build();

        // Assume you have an instance of CPRequestDTO
        CPRequestDTO cpRequestDTO = CPRequestDTO.builder()
                .id(1L)
                .title("Test CPRequestDTO")
                .industry(new IndustryDTO())
                .statusDTO(new CPRequestStatusDTO())
                .comments("Test comments")
                .updatedAt(Instant.now())
                .build();

        // Create Page<CPRequest> and Page<CPRequestDTO>
        List<CPRequest> cpRequestList = Collections.singletonList(cpRequest);
        Page<CPRequest> cpRequestPage = new PageImpl<>(cpRequestList, pageable, cpRequestList.size());

        List<CPRequestDTO> cpRequestDTOList = Collections.singletonList(cpRequestDTO);
        Page<CPRequestDTO> cpRequestDTOPage = new PageImpl<>(cpRequestDTOList, pageable, cpRequestDTOList.size());

        // Given
        given(cpRequestService.findAll(any(Pageable.class), any())).willReturn(cpRequestPage);
        given(mapper.mapToCPRequestDTO(cpRequest)).willReturn(cpRequestDTO);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/cp-request")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", "updatedAt,asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(cpRequestDTO.getId().intValue())));
    }

    @Test
    public void testFindByIdCPRequest() throws Exception {
        // Assume you have an instance of CPRequest
        CPRequest cpRequest = CPRequest.builder()
                .id(1L)
                .title("Test CPRequest")
                .clientPartner(1L)
                .industry(new Industry())
                .status(CPRequestStatus.NEW)
                .comments("Test comments")
                .updatedAt(Instant.now())
                .build();

        // Given
        when(cpRequestService.findById(any(Long.class))).thenReturn(Optional.of(cpRequest));

        // Perform GET request
        this.mockMvc.perform(get("/cp-request/" + 1L))
                .andExpect(status().isOk());
    }

}
