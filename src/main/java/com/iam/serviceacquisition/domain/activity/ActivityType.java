package com.iam.serviceacquisition.domain.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ActivityType {

    SENT_TALENT (1L, "Sent a talent"),
    RECONSIDER_TALENT (2L, "Reconsider talent"),
    APPROVED_TALENT(3L, "Approved talent"),
    COMMENT(4L, "Comment"),
    CREATE_TEAM_REQUEST(5L, "Creates a Team Request"),
    UPLOAD_TALENT_RESUME(6L, "Uploads a Talent Resume"),
    EDIT_TALENT_RESUME(7L, "Edits Talent Resume"),
    REPLACE_TALENT_RESUME(8L, "Replace Talent Resume"),
    COMMENTS_ON_TALENTS(9L, "Comments on a Talent"),
    CANCEL_TEAM_REQUEST(10L, "Cancels a Team Request"),
    EXTEND_DUE_DATE(11L, "Extend Due Date"),
    APPROVED_ALL_TALENTS(12L, "Approves ALL Talents"),
    DETACHED_TALENT_RESUME(13L, "Detached Talent Resume"),
    RE_ASSIGN_NEW_TALENT_MANAGER(14L, "Re Assigning Team Request"),
    COMMENTS_ON_CP_REQUEST(15L, "Comments on a Client Partner Request"),
    COMMENTS_ON_COMBO_REQUEST(16L, "Comments on Combo Team Request");

    private final Long id;
    private final String description;

}
