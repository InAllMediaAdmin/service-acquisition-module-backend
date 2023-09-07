package com.iam.serviceacquisition.service;


import com.iam.serviceacquisition.domain.TeamPositionSlot;
import com.iam.serviceacquisition.domain.activity.Activity;
import com.iam.serviceacquisition.domain.activity.ActivityType;
import com.iam.serviceacquisition.domain.dto.TalentDTO;
import com.iam.serviceacquisition.domain.teamrequest.CPRequest;
import com.iam.serviceacquisition.domain.teamrequest.TeamRequest;
import com.iam.serviceacquisition.repository.ActivityRepository;
import com.iam.user.account.common.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Service
public class ActivityService extends AbstractGenericService<Activity, Long> {

    private final ActivityRepository activityRepository;
    private final UserAccountService userAccountService;

    @Autowired
    public ActivityService(final ActivityRepository activityRepository,
                           final UserAccountService userAccountService) {
        super(activityRepository);
        this.activityRepository = activityRepository;
        this.userAccountService = userAccountService;
    }

    @Transactional
    public Activity logActivity(TalentDTO talent, TeamRequest teamRequest, TeamPositionSlot teamPositionSlot,
                                @NotNull ActivityType activityType, CPRequest cpRequest){
        return createActivity(Activity.builder()
                .talentId(talent.getId())
                .teamRequest(teamRequest)
                .teamPositionSlot(teamPositionSlot)
                .type(activityType)
                .cpRequest(cpRequest)
                .build());
    }

    public List<Activity> findByTeamRequestId(Long teamRequestId){
        return activityRepository.findByTeamRequestId(teamRequestId);
    }

    public List<Activity> findByTeamPositionSlotIdIn(List<Long> slotIds){
        return activityRepository.findByTeamPositionSlotIdIn(slotIds);
    }

    public List<Activity> findByTeamPositionSlotId(Long teamPositionSlotId){
        return activityRepository.findByTeamPositionSlotId(teamPositionSlotId);
    }

    private Activity createActivity (@NotNull Activity activity){
        UserDTO user = userAccountService.getCurrentUser();
        activity.setAuthor(user.getId());
        activity.setCreated(Instant.now());
        return create(activity);
    }
}
