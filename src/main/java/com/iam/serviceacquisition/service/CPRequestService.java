package com.iam.serviceacquisition.service;

import com.iam.mailer.common.exception.EmailException;
import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.domain.Technology;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.repository.CPRequestRepository;
import com.iam.user.account.common.enums.UserRole;
import com.iam.user.account.common.model.UserDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Slf4j
@Service
public class CPRequestService extends AbstractGenericService<CPRequest, Long> {

    private static final String TEMPLATE_TM_REQUEST_TO_PRESALES = "team_tm_requested_to_presales";
    private static final String EMAIL_SUBJECT_REQUEST = "New Team Request !";
    private final CPRequestRepository cpRequestRepository;
    private final UserAccountService userAccountService;
    private final EmailService emailService;

    @Value("${iam.mail.dashboard-url:}")
    private String dashboardUrl;


    public CPRequestService(final CPRequestRepository cpRequestRepository,
                            final UserAccountService userAccountService,
                            final EmailService emailService) {
        super(cpRequestRepository);
        this.cpRequestRepository = cpRequestRepository;
        this.userAccountService = userAccountService;
        this.emailService = emailService;
    }

    public CPRequest createCPRequest(@NonNull CPRequest cpRequest) {
        return create(cpRequest);
    }

    public void sendEmailToPreSales(@NonNull CPRequest cpRequest) {
        HashMap<String, Object> contextMap = createEmailContextMap(cpRequest);

        List<UserDTO> userDTOList = userAccountService.getAllUsers(0, 10,
                "id", UserRole.PRESALES);

        userDTOList.stream().filter(userDTO -> nonNull(userDTO.getEmail())).forEach(preSalesUser -> {
            contextMap.put("psFirstName", preSalesUser.getFirstName());
            try {
                emailService.sendEmail(emailService.buildEmailMessage(contextMap, EMAIL_SUBJECT_REQUEST,
                        preSalesUser.getEmail(), TEMPLATE_TM_REQUEST_TO_PRESALES, getInlineImagesMap()));
            } catch (EmailException e) {
                log.error("There was an error sending an email", e);
            }
        });

    }

    public void sendEmailCommentToPreSales(@NonNull CPRequest cpRequest, UserDTO currentUser) {

        String subject = "New activity in " + cpRequest.getCustomerLead().getLeadProjectName() + " request";

        HashMap<String, Object> contextMap = new HashMap<>();
        contextMap.put("dashboardUrl", dashboardUrl);
        contextMap.put("title", subject);
        contextMap.put("cpFirstName", currentUser.getFirstName() + " " + currentUser.getLastName());
        contextMap.put("leadName", cpRequest.getCustomerLead().getLeadProjectName());
        contextMap.put("leadId", cpRequest.getCustomerLead().getId());

        List<UserDTO> userDTOList = userAccountService.getAllUsers(0, 10,
                "id", UserRole.PRESALES);

        userDTOList.stream().filter(userDTO -> nonNull(userDTO.getEmail())).forEach(preSalesUser -> {
            contextMap.put("psFirstName", preSalesUser.getFirstName());
            try {
                emailService.sendEmail(emailService.buildEmailMessage(contextMap, subject, preSalesUser.getEmail(), "client_partner_comment_to_ps_request",
                        getInlineImagesMap()));
            } catch (EmailException e) {
                log.error("There was an error sending an email", e);
            }
        });

    }

    @Async
    public void sendEmailToClientPartner(@NonNull CPRequest cpRequest, UserDTO currentUser) {
        UserDTO userCP = userAccountService.findUserById(cpRequest.getClientPartner());
        String subject = "New activity in " + cpRequest.getCustomerLead().getLeadProjectName() + " request";

        HashMap<String, Object> contextMap = new HashMap<>();
        contextMap.put("dashboardUrl", dashboardUrl);
        contextMap.put("title", subject);
        contextMap.put("cpFirstName", userCP.getFirstName());
        contextMap.put("presalesFirstName", currentUser.getFirstName());
        contextMap.put("requestId", cpRequest.getId());
        contextMap.put("leadId", cpRequest.getCustomerLead().getId());

        try {
            if (isNotEmpty(userCP.getEmail())) {
                emailService.sendEmail(emailService.buildEmailMessage(contextMap, subject, userCP.getEmail(), "presales_comment_to_cp_request",
                        getInlineImagesMap()));
            } else {
                log.error("No email address present for user CP " + userCP.getId());
            }
        } catch (EmailException e) {
            log.error("There was an error sending an email", e);
        }
    }

    private HashMap<String, Object> createEmailContextMap(CPRequest cpRequest) {
        HashMap<String, Object> contextMap = new HashMap<>();
        String leadTitle = cpRequest.getCustomerLead() != null ? cpRequest.getCustomerLead().getLeadProjectName() : "";
        UserDTO userCP = userAccountService.findUserById(cpRequest.getClientPartner());
        String techDescriptions = cpRequest.getTechnologies().stream()
                .map(Technology::getDescription)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        contextMap.put("dashboardUrl", dashboardUrl);
        contextMap.put("leadTitle", leadTitle);
        contextMap.put("cpFirstName", userCP.getFirstName());
        contextMap.put("cpEmail", userCP.getEmail());
        contextMap.put("industry", cpRequest.getIndustry().getDescription());
        contextMap.put("teamSize", cpRequest.getMinTeamComposition() + " - " + cpRequest.getMaxTeamComposition());
        contextMap.put("rate", cpRequest.getBlendedHourlyRate());
        contextMap.put("technology", techDescriptions);
        return contextMap;
    }

    private Map<String, String> getInlineImagesMap() {
        Map<String, String> inlineImages = new HashMap<>();
        inlineImages.put("icocoderfull", "/templates/img/ico_coderfull.png");
        return inlineImages;
    }

    public void updateStatus(CPRequest cpRequest, CPRequestStatus status) {
        cpRequest.setStatus(status);
        cpRequestRepository.save(cpRequest);
    }
}
