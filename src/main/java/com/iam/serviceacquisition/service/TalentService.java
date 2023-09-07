package com.iam.serviceacquisition.service;

import com.iam.serviceacquisition.domain.Team;
import com.iam.serviceacquisition.domain.dto.SearchRequestDTO;
import com.iam.serviceacquisition.domain.search.dto.SearchTeamResponse;
import com.iam.user.account.common.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TalentService {

    private final UserAccountService userAccountService;

    @Autowired
    public TalentService(final UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    public Page<SearchTeamResponse> getTeamsByCriteria(SearchRequestDTO searchRequestDTO) {
        UserDTO user = userAccountService.getCurrentUser();
        List<Team> teamList = buildTeamsByTalentCriteria(searchRequestDTO);

        return null;
    }

    private List<Team> buildTeamsByTalentCriteria(SearchRequestDTO searchRequestDTO) {
        return null;
    }
}
