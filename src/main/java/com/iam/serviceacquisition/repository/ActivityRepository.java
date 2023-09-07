package com.iam.serviceacquisition.repository;

import com.iam.serviceacquisition.domain.activity.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends GenericRepository<Activity, Long>{

    List<Activity> findByTeamRequestId(Long teamRequestId);

    List<Activity> findByTeamPositionSlotIdIn(List<Long> ids);

    List<Activity> findByTeamPositionSlotId(Long teamPositionSlotId);
}
