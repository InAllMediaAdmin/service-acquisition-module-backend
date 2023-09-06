package com.iam.serviceacquisition.common.enums;

import jakarta.persistence.EntityNotFoundException;

import static java.util.Arrays.asList;

public enum ModalProposalStatus {

    TO_OPEN(1, "To Open"),
    CLOSED(2, "Closed");

    private int id;
    private String label;

    ModalProposalStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return this.label;
    }

    public static ModalProposalStatus fromId(int id) {
        return asList(values()).stream().filter(t -> t.getId() == id).findFirst().orElseThrow(
                () -> new EntityNotFoundException("Modal Proposal Status not found"));
    }

    public static ModalProposalStatus fromLabel(String label) {
        return asList(values()).stream().filter(t -> t.getLabel().equals(label)).findFirst().orElseThrow(
                () -> new EntityNotFoundException("Modal Proposal Status not found"));
    }
}
