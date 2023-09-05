package com.iam.serviceacquisition.common.enums;

import com.iam.serviceacquisition.mapper.MappingUtil;
import jakarta.persistence.EntityNotFoundException;

public enum Gender {

    MALE(1,"Male"), FEMALE(2,"Female");

    private final Integer id;
    private final String label;

    Gender(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Integer getId() {
        return this.id;
    }

    public static Gender fromId(Integer id) {
        for (Gender gender : Gender.values()) {
            if (gender.getId().equals(id)) {
                return gender;
            }
        }

        throw new EntityNotFoundException(String.format("Gender not found for gender id %s", id));
    }

    public static Gender fromDescription(String description) {
        return MappingUtil.findClosestStringMatch(Gender.values(),"getLabel", description, MALE);
    }
}
