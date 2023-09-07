package com.iam.serviceacquisition.repository;

import com.iam.serviceacquisition.domain.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends GenericRepository<Company, Long> {
}
