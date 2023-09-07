package com.iam.serviceacquisition.repository;

import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface CPRequestRepository extends GenericRepository<CPRequest, Long> {
}
