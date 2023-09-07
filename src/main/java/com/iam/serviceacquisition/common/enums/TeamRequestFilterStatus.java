package com.iam.serviceacquisition.common.enums;

public enum TeamRequestFilterStatus {

    DRAFT(1, "Draft"),
    IN_PROGRESS(2, "In Progress"),
    SHARED_WITH_CLIENT(3, "Shared with client");

    private int id;
    private String label;

    TeamRequestFilterStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return this.label;
    }

}
