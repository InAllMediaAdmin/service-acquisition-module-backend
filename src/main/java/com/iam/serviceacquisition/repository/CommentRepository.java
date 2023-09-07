package com.iam.serviceacquisition.repository;


import com.iam.serviceacquisition.domain.activity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends GenericRepository<Comment, Long>{

    Page<Comment> findByActivityTalentId(Pageable pageable, Long talentId);

    List<Comment> findByActivityTeamPositionSlotId(Long positionSlotId);

    Page<Comment> findByActivityTeamPositionSlotId(Pageable pageable, Long positionSlotId);

    Page<Comment> findByActivityCpRequestId(Pageable pageable, Long cpRequestId);

    Optional<Comment> findTopByActivityCpRequestIdOrderByIdDesc(Long cpRequestId);
}
