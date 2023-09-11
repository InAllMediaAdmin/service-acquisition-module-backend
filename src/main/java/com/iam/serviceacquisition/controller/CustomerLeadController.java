package com.iam.serviceacquisition.controller;

import com.iam.serviceacquisition.common.enums.TeamRequestFilterStatus;
import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import com.iam.serviceacquisition.domain.CustomerLead;
import com.iam.serviceacquisition.domain.activity.Activity;
import com.iam.serviceacquisition.domain.activity.ActivityType;
import com.iam.serviceacquisition.domain.activity.Comment;
import com.iam.serviceacquisition.domain.dto.CardTeamRequestDTO;
import com.iam.serviceacquisition.domain.dto.CustomerLeadDTO;
import com.iam.serviceacquisition.domain.dto.TeamProposalDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.mapper.CustomerLeadMapper;
import com.iam.serviceacquisition.service.ActivityService;
import com.iam.serviceacquisition.service.CommentService;
import com.iam.serviceacquisition.service.CustomerLeadService;
import com.iam.serviceacquisition.service.UserAccountService;
import com.iam.user.account.common.model.UserDTO;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customerLead")
@Slf4j
public class CustomerLeadController {

    private final CustomerLeadService customerLeadService;
    private final UserAccountService userAccountService;
    private final ActivityService activityService;
    private final CommentService commentService;

    private final CustomerLeadMapper mapper;

    @ApiOperation(value = "Retrieve Customer Lead by Id", produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerLeadDTO> findById(@PathVariable("id") final Optional<CustomerLead> customerLead) {
        if (customerLead.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToCustomerLeadDTO(customerLead.get()));
    }

    @ApiOperation(value = "Create or Update Customer Lead")
    @PutMapping
    public ResponseEntity<CustomerLeadDTO> createOrUpdateCustomerLead(@Valid @RequestBody final CustomerLeadDTO customerLeadDTO) {
        log.info("Creating or Updating Customer Lead {} ", customerLeadDTO);

        CustomerLead customerLeadMapped = mapper.mapToCustomerLeadEntity(customerLeadDTO);
        if (isNull(customerLeadMapped.getClientPartner())){
            UserDTO currentUser = userAccountService.getCurrentUser();
            customerLeadMapped.setClientPartner(currentUser.getId());
        }

        return ResponseEntity.ok(mapper.mapToCustomerLeadDTO(customerLeadService.createCustomerLead(customerLeadMapped)));
    }

    @ApiOperation(
            value = "Share With Client Partner by Customer Lead by Id", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PutMapping(value = "/{customerLeadId}/share-with-cp/{cpRequestId}")
    public ResponseEntity shareWithCP(@PathVariable("customerLeadId") final Optional<CustomerLead> customerLead,
                                      @PathVariable("cpRequestId") final Optional<CPRequest> cpRequest,
                                      @RequestBody final String commentBody) {
        if (customerLead.isEmpty() || cpRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        customerLeadService.shareWithCP(customerLead.get(), cpRequest.get());

        Activity activity = activityService.logActivity(null, null, null,
                ActivityType.COMMENTS_ON_CP_REQUEST, cpRequest.get());
        Comment comment = Comment.builder().activity(activity).body(commentBody).build();
        commentService.create(comment);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete Customer Lead")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerLeadDTO> deleteCustomerLead(@PathVariable("id") final Optional<CustomerLead> customerLead) {
        if (customerLead.isEmpty()){
            throw new EntityNotFoundException("Customer Lead was not found");
        }
        log.info("Deleting Customer Lead with ID {} ", customerLead.get().getId());

        customerLeadService.delete(customerLead.get());

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all Customer Lead paginated")
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
    public ResponseEntity<Page<CustomerLeadDTO>> getAllCustomerLeads(Pageable pageable,
            @RequestParam(name = "companyName", required = false) Optional<String> companyName,
            @RequestParam(name = "teamRequestStatusListFilter", required = false) List<TeamRequestStatus> teamRequestStatusListFilter,
            @RequestParam(name = "industryId", required = false) Optional<Long> industryId,
            @RequestParam(name = "contactName", required = false) Optional<String> contactName,
            @RequestParam(name = "leadProjectName", required = false) Optional<String> leadProjectName,
            @RequestParam(name = "isArchived", required = false, defaultValue = "false") boolean isArchived,
            @RequestParam(name = "lastRequestStatus", required = false) Optional<TeamRequestFilterStatus> lastRequestStatus)
    {

        Page<CustomerLead> customerLeadPage = customerLeadService.findCustomerLeadLandingPage(pageable,
                companyName, teamRequestStatusListFilter, industryId, contactName, leadProjectName, isArchived, lastRequestStatus);

        return ResponseEntity.ok(customerLeadPage.map(mapper::mapToCustomerLeadDTO));

    }

    @ApiOperation(value = "Retrieve Customer Lead by Id", produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/teamProposalsByLead/{id}")
    public ResponseEntity<List<TeamProposalDTO>> findProposalsByCustomerLead(@PathVariable("id") final Optional<CustomerLead> customerLead,
                                                                             @RequestParam(name = "statuses", required = false) List<Long> statusIds) {
        if (customerLead.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(customerLeadService.findProposalsByCustomerLead(customerLead.get(), statusIds));
    }

    @ApiOperation(value = "Retrieve Team Request by Customer Lead by Id", produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/teamRequestCardByLead/{id}")
    public ResponseEntity<CardTeamRequestDTO> findTeamRequestCardByCustomerLead(@PathVariable("id") final Optional<CustomerLead> customerLead) {
        if (customerLead.isEmpty() || Collections.isEmpty(customerLead.get().getTeamRequests())) {
            return ResponseEntity.notFound().build();
        }
        CardTeamRequestDTO cardTeamRequestDTO = customerLeadService.findTeamRequestCardByCustomerLead(customerLead.get());
        return ResponseEntity.ok(cardTeamRequestDTO);

    }

}
