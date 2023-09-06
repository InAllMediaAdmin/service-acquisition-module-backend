package com.iam.serviceacquisition.common.enums;

import lombok.Getter;

import jakarta.persistence.EntityNotFoundException;

@Getter
public enum CPRequestStatus {

    NEW(1L, "New"),
    IN_PROGRESS(2L, "In Progress"),
    COMPLETED(3L, "Completed"),
    SHARED_WITH_CP(4L, "Shared with CP");

    private final long id;
    private final String label;

    CPRequestStatus(long id, String label) {
        this.id = id;
        this.label = label;
    }

    public static CPRequestStatus fromId(long id) {
        for (CPRequestStatus r : CPRequestStatus.values()) {
            if (r.id == id) {
                return r;
            }
        }
        throw new EntityNotFoundException(String.format("CP Request status not found for level id %s", id));
    }
}
