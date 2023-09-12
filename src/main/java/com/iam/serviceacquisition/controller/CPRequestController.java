package com.iam.serviceacquisition.controller;

import com.iam.serviceacquisition.common.enums.CPRequestStatus;

import com.iam.serviceacquisition.common.util.UUIDUtils;
import com.iam.serviceacquisition.domain.Industry;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.dto.CPRequestDTO;
import com.iam.serviceacquisition.mapper.CPRequestMapper;
import com.iam.serviceacquisition.service.CPRequestService;
import com.iam.serviceacquisition.service.UserAccountService;
import com.iam.serviceacquisition.specification.CPRequestSpecification;
import com.iam.serviceacquisition.util.SpecificationUtils;
import com.iam.user.account.common.enums.UserRole;
import com.iam.user.account.common.model.UserDTO;
import com.iam.user.account.common.util.AuthenticationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cp-request")
@Slf4j
public class CPRequestController {

    private final CPRequestService cpRequestService;
    private final UserAccountService userAccountService;
    private final CPRequestMapper cpRequestMapper;

    @ApiOperation(
            value = "Create or update Client Partner Request", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PostMapping()
    public ResponseEntity<CPRequestDTO> createOrUpdate(@Valid @RequestBody final CPRequestDTO cpRequestDTO) {
        CPRequest cpRequest = cpRequestService.createCPRequest(cpRequestMapper.mapToCPRequestEntity(cpRequestDTO));
        ForkJoinPool.commonPool().execute(() -> {
            AuthenticationUtils.configureAuthentication(UUIDUtils.randomUUID(), UUIDUtils.randomUUID(),
                    List.of(UserRole.ADMIN));

            cpRequestService.sendEmailToPreSales(cpRequest);

            AuthenticationUtils.clearAuthentication();
        });
        return ResponseEntity.ok(cpRequestMapper.mapToCPRequestDTO(cpRequest));
    }

    @ApiOperation(value = "Client Partner Request by Id", produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{id}")
    public ResponseEntity<CPRequestDTO> findById(@PathVariable Long id) {
        final CPRequest cpRequest = cpRequestService.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Client Partner Request not found"));
        return ResponseEntity.status(HttpStatus.OK).body(cpRequestMapper.mapToCPRequestDTO(cpRequest));
    }

    @GetMapping()
    @ApiOperation(value = "Get all Client Partner Request", produces = APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    public ResponseEntity<Page<CPRequestDTO>> clientPartnerRequests(
            @RequestParam(name = "statuses", required = false) List<Long> statusIds,
            @RequestParam(name = "industry", required = false) Optional<Industry> industry,
            @RequestParam(name = "title", required = false) Optional<String> title,
            @RequestParam(name = "cpName", required = false) Optional<String> cpName,
            Pageable pageable) {

        List<Long> cpIds = new ArrayList<>();
        if (cpName.isPresent()){
            cpIds.addAll(userAccountService.getUsersWithFullNameContainingAndRole(cpName.get(),UserRole.CLIENT_PARTNER)
                    .stream().map(UserDTO::getId).collect(Collectors.toList()));
            if (cpIds.isEmpty()){
                cpIds.add(-1L);
            }
        }

        List<CPRequestStatus> statuses = CollectionUtils.isEmpty(statusIds) ? List.of()
                : statusIds.stream().map(CPRequestStatus::fromId).collect(Collectors.toList());

        Page<CPRequest> cpr = cpRequestService.findAll(pageable,
                SpecificationUtils.combine(
                        CPRequestSpecification.withIndustry(industry),
                        CPRequestSpecification.withStatuses(statuses),
                        CPRequestSpecification.withTitle(title),
                        CPRequestSpecification.withCPIds(cpIds)
                )
        );

        return new ResponseEntity<>(cpr.map(c -> cpRequestMapper.mapToCPRequestDTO(c)),
                new HttpHeaders(), HttpStatus.OK);
    }

}
