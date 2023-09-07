package com.iam.serviceacquisition.service;


import com.iam.serviceacquisition.domain.Company;
import com.iam.serviceacquisition.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends AbstractGenericService<Company, Long>{
    private final CompanyRepository companyRepository;

    public CompanyService(final CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

}
