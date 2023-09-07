package com.iam.serviceacquisition.repository;


import com.iam.serviceacquisition.common.enums.TeamRequestStatus;
import com.iam.serviceacquisition.domain.CustomerLead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerLeadRepository extends GenericRepository<CustomerLead, Long> {

    @Query(value = "select cl " +
            "from CustomerLead cl " +
            "     left join TeamRequest tr on cl.id = tr.customerLead.id " +
            "                               AND tr.id = ( select MAX(id) " +
            "                                             from TeamRequest " +
            "                                             where customerLead.id = cl.id ) " +
            "     left join cl.company c " +
            "     left join cl.stakeholder s " +
            "Where 1=1 " +
            "      AND (:companyName is null or c.name like %:companyName%) " +
            "      AND (:industryId is null or cl.industry.id = :industryId) " +
            "      AND (:contactName is null or CONCAT(s.firstName, ' ', s.lastName) like %:contactName%) " +
            "      AND (:leadProjectName is null or cl.leadProjectName like %:leadProjectName%) " +
            "      AND ((tr.id IS NULL AND :isArchived = false) OR (:hasFilterNotInStatus = true) OR tr.status in (:listTeamRequestStatus)) " +
            "      AND (:hasFilterNotInStatus = false OR tr.status not in ('CANCELED', 'SHARED_WITH_CLIENT', " +
            "           'COMBO_SHARED', 'COMBO_IN_PROGRESS', 'COMBO_APPROVED')) " +
            "      AND (:hasNoTeamRequest = false OR tr.id IS NULL) " +
            "      AND (:hasStatusSharedWithClient = false OR tr.status in ('SHARED_WITH_CLIENT', 'COMBO_SHARED')) " +
            "ORDER BY cl.created DESC ")
    Page<CustomerLead> findCustomerLeadLandingPage(Pageable pageable,
                                                   Optional<String> companyName,
                                                   List<TeamRequestStatus> listTeamRequestStatus,
                                                   Optional<Long> industryId,
                                                   Optional<String> contactName,
                                                   Optional<String> leadProjectName,
                                                   boolean isArchived,
                                                   boolean hasFilterNotInStatus,
                                                   boolean hasNoTeamRequest,
                                                   boolean hasStatusSharedWithClient);
}
