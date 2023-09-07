package com.iam.serviceacquisition.common.enums;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;

public enum SearchPositionSlotType {

    STANDARD(0),ADDITIONAL(1);

    private final Integer id;

    public Integer getId() {
        return this.id;
    }

    SearchPositionSlotType(Integer id) {
        this.id = id;
    }

    public static SearchPositionSlotType fromId(Integer id) {
        return Arrays.stream(SearchPositionSlotType.values())
                .filter(a -> a.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("SearchPositionSlotType not found"));
    }
}
