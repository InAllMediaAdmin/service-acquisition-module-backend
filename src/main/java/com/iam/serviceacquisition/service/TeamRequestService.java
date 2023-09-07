package com.iam.serviceacquisition.service;

import com.iam.serviceacquisition.domain.TeamPositionSlot;
import com.iam.serviceacquisition.domain.dto.CardTeamRequestRolesDTO;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeamRequestService {


    public List<CardTeamRequestRolesDTO> getCardTeamRequestRolesDTOS(List<TeamPositionSlot> teamPositionSlots) {
        //TODO: This is part of Team Request to be implemented
        return null;
    }
}
