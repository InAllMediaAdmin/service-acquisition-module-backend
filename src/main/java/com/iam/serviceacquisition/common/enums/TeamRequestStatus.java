package com.iam.serviceacquisition.common.enums;

import jakarta.persistence.EntityNotFoundException;

import static java.util.Arrays.asList;

public enum TeamRequestStatus {

    IN_PROGRESS(1, "In Progress"),
    BOOKED(2, "Booked"),
    OPPORTUNITY(3, "Opportunity"),
    BUILDING_PROPOSAL(4, "Building proposal"),
    CANCELED(5, "Canceled"),
    SHARED_WITH_CLIENT(6, "Shared with client"),
    COMBO_IN_PROGRESS(7, "Combo In Progress"),
    COMBO_APPROVED(8, "Combo approved"),
    COMBO_SHARED(9, "Combo shared");

    private int id;
    private String label;

    TeamRequestStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return this.label;
    }

    public static TeamRequestStatus fromId(int id) {
        return asList(values()).stream().filter(t -> t.getId() == id).findFirst().orElseThrow(
                () -> new EntityNotFoundException("Team Request Status not found"));
    }
}
