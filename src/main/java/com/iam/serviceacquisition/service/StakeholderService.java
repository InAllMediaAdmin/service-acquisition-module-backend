package com.iam.serviceacquisition.service;


import com.iam.serviceacquisition.domain.Stakeholder;
import com.iam.serviceacquisition.repository.StakeholderRepository;
import org.springframework.stereotype.Service;

@Service
public class StakeholderService extends AbstractGenericService<Stakeholder, Long>{

    private final StakeholderRepository stakeholderRepository;

    public StakeholderService(final StakeholderRepository stakeholderRepository) {
        super(stakeholderRepository);
        this.stakeholderRepository = stakeholderRepository;
    }

}
