package com.iam.serviceacquisition.service;

import com.iam.mailer.common.exception.EmailException;
import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.common.enums.TeamRequestFilterStatus;
import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import com.iam.serviceacquisition.domain.*;
import com.iam.serviceacquisition.domain.dto.CardTeamRequestDTO;
import com.iam.serviceacquisition.domain.dto.CardTeamRequestRolesDTO;
import com.iam.serviceacquisition.domain.dto.TeamProposalDTO;
import com.iam.serviceacquisition.domain.dto.TechnologyTeamDeckDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import com.iam.serviceacquisition.repository.CustomerLeadRepository;
import com.iam.user.account.common.model.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class CustomerLeadService extends AbstractGenericService<CustomerLead, Long> {

    @Value("${iam.mail.dashboard-url:}")
    private String dashboardUrl;
    private final CustomerLeadRepository customerLeadRepository;
    private final CompanyService companyService;
    private final StakeholderService stakeholderService;
    private final CPRequestService cpRequestService;
    private final UserAccountService userAccountService;
    private final EmailService emailService;
    private final TeamRequestService teamRequestService;


    @Autowired
    public CustomerLeadService(final CustomerLeadRepository customerLeadRepository,
                               final CompanyService companyService,
                               final StakeholderService stakeholderService,
                               final CPRequestService cpRequestService,
                               final UserAccountService userAccountService,
                               final EmailService emailService,
                               @Lazy final TeamRequestService teamRequestService) {

        super(customerLeadRepository);
        this.customerLeadRepository = customerLeadRepository;
        this.companyService = companyService;
        this.stakeholderService = stakeholderService;
        this.cpRequestService = cpRequestService;
        this.userAccountService = userAccountService;
        this.emailService = emailService;
        this.teamRequestService = teamRequestService;
    }

    public Page<CustomerLead> findCustomerLeadLandingPage(Pageable pageable,
                                                          Optional<String> companyName,
                                                          List<TeamRequestStatus> teamRequestStatusListFilter,
                                                          Optional<Long> industryId,
                                                          Optional<String> contactName,
                                                          Optional<String> leadProjectName,
                                                          boolean isArchived,
                                                          Optional<TeamRequestFilterStatus> lastRequestStatus) {
        if (isArchived){
            teamRequestStatusListFilter = List.of(TeamRequestStatus.CANCELED);
        } else if (CollectionUtils.isEmpty(teamRequestStatusListFilter)) {
            teamRequestStatusListFilter = List.of(TeamRequestStatus.IN_PROGRESS, TeamRequestStatus.BOOKED,
                    TeamRequestStatus.OPPORTUNITY, TeamRequestStatus.BUILDING_PROPOSAL, TeamRequestStatus.SHARED_WITH_CLIENT,
                    TeamRequestStatus.COMBO_SHARED);
        }

        boolean hasFilterNotInStatus = false;
        boolean hasNoTeamRequest = false;
        boolean hasStatusSharedWithClient = false;
        if (lastRequestStatus.isPresent()) {
            if (lastRequestStatus.get().equals(TeamRequestFilterStatus.SHARED_WITH_CLIENT)) {
                hasStatusSharedWithClient = true;
            } else if (lastRequestStatus.get().equals(TeamRequestFilterStatus.IN_PROGRESS)) {
                hasFilterNotInStatus = true;
            } else if (lastRequestStatus.get().equals(TeamRequestFilterStatus.DRAFT)) {
                hasNoTeamRequest = true;
            }
        }

        return customerLeadRepository.findCustomerLeadLandingPage(pageable, companyName, teamRequestStatusListFilter,
                industryId, contactName, leadProjectName, isArchived, hasFilterNotInStatus,
                hasNoTeamRequest, hasStatusSharedWithClient);
    }

    @Transactional
    public CustomerLead createCustomerLead(CustomerLead customerLead) {

        Company company = companyService.create(customerLead.getCompany());
        customerLead.setCompany(company);

        Stakeholder stakeholder = stakeholderService.create(customerLead.getStakeholder());
        customerLead.setStakeholder(stakeholder);

        if (isNull(customerLead.getId())) {
            customerLead.setCreated(Instant.now());
        }

        return create(customerLead);
    }

    public List<TeamProposalDTO> findProposalsByCustomerLead(CustomerLead customerLead, List<Long> statusIds) {
        List<TeamRequest> sortedRequests = customerLead.getTeamRequests().stream()
                .filter(request -> nonNull(request.getProposal()))
                .sorted(Comparator.comparing(TeamRequest::getRequestedDate))
                .collect(Collectors.toList());

        Set<TechnologyTeamDeckDTO> techSet = new HashSet<>(); // To ensure no duplicate technologies

        return IntStream.range(0, sortedRequests.size())
                .mapToObj(i -> {
                    TeamRequest request = sortedRequests.get(i);
                    TeamProposal proposal = request.getProposal();
                    Team team = request.getTeam();
                    List<TechnologyTeamDeckDTO> techDTOs = team.getTeamPositionSlots().stream()
                            .map(slot -> slot.getTalent().getMainTechnologies())
                            .flatMap(List::stream) // Convert Stream<List<Technology>> to Stream<Technology>
                            .distinct() // Ensure no duplicates
                            .map(tech -> TechnologyTeamDeckDTO.builder()
                                    .id(tech.getId())
                                    .description(tech.getDescription())
                                    .build()) // Convert to DTOs
                            .collect(Collectors.toList());
                    techSet.addAll(techDTOs);

                    return TeamProposalDTO.builder()
                            .id(nonNull(proposal) ? proposal.getId() : 0L)
                            .created(nonNull(proposal) ? proposal.getCreated() : null)
                            .teamName(team.getTeamName())
                            .blendedRate(nonNull(proposal) ? proposal.getBlendedRate(): null)
                            .mainTechnologies(new ArrayList<>(techSet))
                            .membersSize(team.getTeamPositionSlots().size())
                            //.versionNumber(i+1)
                            .versionNumber(1)
                            .status(request.getStatus())
                            .teamRequestId(request.getId())
                            .build();
                })
                .sorted(Comparator.comparing(TeamProposalDTO::getVersionNumber).reversed())
                .filter(proposal -> statusIds == null || statusIds.isEmpty() || statusIds.contains(proposal.getStatus().getId()))
                .collect(Collectors.toList());
    }


    public CardTeamRequestDTO findTeamRequestCardByCustomerLead(CustomerLead customerLead) {
        List<TeamRequest> teamRequestList = customerLead.getTeamRequests();

        TeamRequest lastTeamRequest = teamRequestList.stream()
                .reduce((first, second) -> second)
                .orElse(null);

        // Extract SearchPositionSlot objects from TeamPositionSlots
        List<TeamPositionSlot> teamPositionSlots = lastTeamRequest.getTeam().getTeamPositionSlots().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<CardTeamRequestRolesDTO> cardTeamRequestRolesList = teamRequestService.getCardTeamRequestRolesDTOS(teamPositionSlots);

        CPRequest cpRequest = customerLead.getCpRequests().stream()
                .max(Comparator.comparing(CPRequest::getId))
                .orElseThrow(() -> new EntityNotFoundException("CPRequest not found"));

        return CardTeamRequestDTO.builder().roles(cardTeamRequestRolesList)
                .teamSize(teamPositionSlots.size())
                .teamRequestId(lastTeamRequest.getId())
                .industry(cpRequest.getIndustry().getDescription())
                .blendedHourlyRate(cpRequest.getBlendedHourlyRate())
                .build();
    }

    public void shareWithCP(CustomerLead customerLead, CPRequest cpRequest) {
        cpRequest.setStatus(CPRequestStatus.SHARED_WITH_CP);
        UserDTO currentUser = userAccountService.getCurrentUser();
        cpRequest.setPreSalesUserId(currentUser.getId());
        cpRequestService.create(cpRequest);
        notifyCPTeamApproved(customerLead);
    }

    public void notifyCPTeamApproved(@NonNull CustomerLead customerLead) {
        HashMap<String, Object> contextMap = createEmailContextMap(customerLead);
        String template = "team_deck_ready_cp_review";
        String str = contextMap.get("leadTitle") != null ? "for " + contextMap.get("leadTitle") : "";
        String subject = "Team deck " + str + " now is ready!";
        sendEmailToClientPartner(customerLead, template, subject, contextMap);
    }

    @Async
    public void sendEmailToClientPartner(@NonNull CustomerLead customerLead,
                                         @NonNull String template,
                                         @NonNull String subject,
                                         HashMap<String, Object> contextMap) {
        if (CollectionUtils.isEmpty(contextMap)) {
            contextMap = createEmailContextMap(customerLead);
        }
        try {
            String cpEmail = contextMap.get("cpEmail") == null ?
                    userAccountService.findUserById(customerLead.getClientPartner()).getEmail() : String.valueOf(contextMap.get("cpEmail"));
            emailService.sendEmail(emailService.buildEmailMessage(contextMap, subject, cpEmail, template,
                    getInlineImagesMap()));
        } catch (EmailException e) {
            log.error("There was an error sending an email", e);
        }
    }

    private HashMap<String, Object> createEmailContextMap(CustomerLead customerLead) {
        HashMap<String, Object> contextMap = new HashMap<>();
        String leadTitle = customerLead.getLeadProjectName() != null ? customerLead.getLeadProjectName() : "";
        UserDTO userCP = userAccountService.findUserById(customerLead.getClientPartner());

        contextMap.put("dashboardUrl", dashboardUrl);
        contextMap.put("leadTitle", leadTitle);
        contextMap.put("cpFirstName", userCP.getFirstName());
        contextMap.put("cpEmail", userCP.getEmail());
        contextMap.put("leadId", customerLead.getId());

        return contextMap;
    }

    private Map<String, String> getInlineImagesMap() {
        Map<String, String> inlineImages = new HashMap<>();
        inlineImages.put("icocoderfull", "/templates/img/ico_coderfull.png");
        return inlineImages;
    }
}
