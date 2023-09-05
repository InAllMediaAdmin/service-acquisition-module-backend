package com.iam.serviceacquisition.common.enums;

import java.util.EnumSet;
import java.util.Set;
import jakarta.persistence.EntityNotFoundException;

import static java.util.Arrays.asList;

public enum TalentState {

    REAL(1),FAKE(2),ADHOC(3), HIDDEN(4),
    CLONE(5), DRAFT(6);

    private Integer id;

    TalentState(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static TalentState fromId(Integer id) {
        return asList(TalentState.values()).stream().filter(a -> a.id == id).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("TalentState not found"));
    }

    public static Set<TalentState> listNonSearchableTalentStates() {
        return EnumSet.of(HIDDEN, CLONE, DRAFT);
    }
}
