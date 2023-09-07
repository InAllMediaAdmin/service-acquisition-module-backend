package com.iam.serviceacquisition.service;


import com.iam.serviceacquisition.common.enums.CPRequestStatus;
import com.iam.serviceacquisition.common.util.UUIDUtils;
import com.iam.serviceacquisition.domain.activity.Activity;
import com.iam.serviceacquisition.domain.activity.Comment;
import com.iam.serviceacquisition.repository.CommentRepository;
import com.iam.user.account.common.enums.UserRole;
import com.iam.user.account.common.model.UserDTO;
import com.iam.user.account.common.util.AuthenticationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

import static com.iam.user.account.common.enums.UserRole.CLIENT_PARTNER;
import static com.iam.user.account.common.enums.UserRole.TALENT_MANAGER;

@Service
@Slf4j
public class CommentService extends AbstractGenericService<Comment, Long> {

    private final CommentRepository commentRepository;
    private final UserAccountService userAccountService;
    private final CPRequestService cpRequestService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserAccountService userAccountService,
                          CPRequestService cpRequestService) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.userAccountService = userAccountService;
        this.cpRequestService = cpRequestService;
    }

    public Page<Comment> findByTalentId(PageRequest pageRequest, Long talentId) {
        return commentRepository.findByActivityTalentId(pageRequest, talentId);
    }

    public List<Comment> findByTeamPositionSlot(Long teamPositionSlotId) {
        return commentRepository.findByActivityTeamPositionSlotId(teamPositionSlotId);
    }

    public Page<Comment> findByTeamPositionSlot(PageRequest pageRequest, Long teamPositionSlotId) {
        return commentRepository.findByActivityTeamPositionSlotId(pageRequest, teamPositionSlotId);
    }

    public Comment readComment(Comment comment, UserDTO currentUser) {

        if ((comment.getActivity().getTeamRequest() != null
                && currentUser.getId() == comment.getActivity().getTeamRequest().getClientPartner())
                || currentUser.getRoles().stream().anyMatch(r -> r.getId().equals(CLIENT_PARTNER.getId()))) {
            comment.setReadByCP(true);
        }

        if ((comment.getActivity().getTeamRequest() != null
                && currentUser.getId() == comment.getActivity().getTeamRequest().getTalentAgent())
                || currentUser.getRoles().stream().anyMatch(r -> r.getId().equals(TALENT_MANAGER.getId()))) {
            comment.setReadByTM(true);
        }

        return commentRepository.save(comment);

    }

    public Comment registerComment(Activity activity, Comment comment){

        comment.setActivity(activity);

        return this.create(comment);
    }

    public Page<Comment> findByActivityCpRequestId(PageRequest pageRequest, Long cpRequestId) {
        return commentRepository.findByActivityCpRequestId(pageRequest, cpRequestId);
    }

    public Comment saveCommentForCPRequest(Comment comment) {
        UserDTO currentUser = userAccountService.getCurrentUser();

        Comment commentCreated = this.create(comment);

        if (currentUser.isClientPartner()){
            ForkJoinPool.commonPool().execute(() -> {
                AuthenticationUtils.configureAuthentication(UUIDUtils.randomUUID(), UUIDUtils.randomUUID(),
                        List.of(UserRole.ADMIN));

                cpRequestService.sendEmailCommentToPreSales(comment.getActivity().getCpRequest(), currentUser);

                AuthenticationUtils.clearAuthentication();
            });
            cpRequestService.updateStatus(comment.getActivity().getCpRequest(), CPRequestStatus.IN_PROGRESS);
        } else {
            cpRequestService.sendEmailToClientPartner(comment.getActivity().getCpRequest(), currentUser);
            cpRequestService.updateStatus(comment.getActivity().getCpRequest(), CPRequestStatus.SHARED_WITH_CP);
        }

        return commentCreated;
    }

    public Optional<Comment> findTopByActivityCpRequestIdOrderByIdDesc(Long id) {
        return commentRepository.findTopByActivityCpRequestIdOrderByIdDesc(id);
    }
}
