package com.iam.serviceacquisition.common.enums;

import com.iam.serviceacquisition.mapper.MappingUtil;
import com.iam.serviceacquisition.model.dto.LevelDTO;
import jakarta.persistence.EntityNotFoundException;


import java.util.*;

public enum TalentLevel {

    TECH_CONSULTANT(1L,"Tech Consultant", 70),
    TECH_ARCHITECT(2L, "Tech Architect", 60),
    TECH_LEAD(3L,"Tech Lead", 50),
    SENIOR(4L, "Senior", 30),
    MID_LEVEL(5L, "Mid Level", 20),
    ENTRY_LEVEL(6L, "Entry Level", 10);

    private final Long id;
    private final String label;
    private final int level;

    TalentLevel(Long id, String label, int level) {
        this.id = id;
        this.label = label;
        this.level = level;
    }

    public String getLabel() {
        return this.label;
    }

    public Long getId() {
        return this.id;
    }

    public int getLevel() {
        return level;
    }

    public static TalentLevel fromId(Long id) {
        for (TalentLevel r : TalentLevel.values()) {
            if (r.id.equals(id)) {
                return r;
            }
        }
        throw new EntityNotFoundException(String.format("Seniority Level not found for level id %s", id));
    }

    public static List<LevelDTO> getAllLevels () {
        List<LevelDTO> levelDTOs = new ArrayList<>();
        List<TalentLevel> talentLevels = Arrays.asList(TalentLevel.values());
        talentLevels.stream()
                .filter(tl -> !tl.equals(ENTRY_LEVEL))
                .sorted(Comparator.comparing(TalentLevel::getLabel, String.CASE_INSENSITIVE_ORDER))
                .forEach(l -> {
                    LevelDTO levelDTO = new LevelDTO(l.getId(), l.getLabel());
                    levelDTOs.add(levelDTO);
                });
        return levelDTOs;
    }

    public static Optional<LevelDTO> findLevelById (Long levelId) {
        List<TalentLevel> talentLevels = Arrays.asList(TalentLevel.values());
        Optional<TalentLevel> talentLevelOptional = talentLevels.stream()
                .filter(l -> l.getId().equals(levelId)).findFirst();
        if (talentLevelOptional.isPresent()){
            TalentLevel talentLevel = talentLevelOptional.get();
            LevelDTO levelDTO = new LevelDTO(talentLevel.getId(), talentLevel.getLabel());
            return Optional.of(levelDTO);
        }
        return Optional.empty();
    }


    public static TalentLevel fromDescription(String description) {
        return MappingUtil.findClosestStringMatch(TalentLevel.values(),"getLabel", description, MID_LEVEL);
    }
}
