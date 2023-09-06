package com.iam.serviceacquisition.common.enums;

import com.iam.serviceacquisition.domain.talentresume.dto.TalentResumeStateDTO;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum TalentResumeState {

    @Deprecated
    IN_PROGRESS(1, "In Progress"),
    PENDING_PS_REVIEW(2, "Pending PS Review"),
    PENDING_CP_REVIEW(3, "Pending CP Review"),
    APPROVED(4, "Approved"),
    RECONSIDER(5, "Reconsider");

    private final Integer id;
    private final String label;

    TalentResumeState(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return this.label;
    }

    public static TalentResumeState fromId(Integer id) {
        return Arrays.stream(TalentResumeState.values())
                .filter(a -> a.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Talent Resume State not found"));
    }

    public static List<TalentResumeStateDTO> getAllTalentResumeStates() {
        List<TalentResumeStateDTO> statesDTOs = new ArrayList<>();
        List<TalentResumeState> states = Arrays.asList(TalentResumeState.values());
        states.stream()
                .sorted(Comparator.comparing(TalentResumeState::getLabel, String.CASE_INSENSITIVE_ORDER))
                .forEach(l -> {
                    TalentResumeStateDTO stateDTO = new TalentResumeStateDTO(l.getId(), l.getLabel());
                    statesDTOs.add(stateDTO);
                });
        return statesDTOs;
    }
}
