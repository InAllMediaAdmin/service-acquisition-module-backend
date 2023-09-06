package com.iam.serviceacquisition.common.enums;

import com.iam.serviceacquisition.domain.dto.EnglishLevelDTO;

import jakarta.persistence.EntityNotFoundException;
import java.util.*;

public enum EnglishLevel {

    BEGINNER(10, "Beginner", 20),
    INTERMEDIATE(20, "Intermediate", 30),
    UPPER_INTERMEDIATE(30,"Upper-Intermediate", 40),
    ADVANCED(40, "Advanced", 50),
    NATIVE(50, "Native", 50);

    private final Integer id;
    private final String label;
    private final Integer next;

    EnglishLevel(Integer id, String label, Integer next) {
        this.id = id;
        this.label = label;
        this.next = next;
    }

    public String getLabel() {
        return this.label;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getNext() {
        return this.next;
    }

    public static EnglishLevel fromId(Integer id) {
        for (EnglishLevel el : EnglishLevel.values()) {
            if (el.id.equals(id)) {
                return el;
            }
        }
        throw new EntityNotFoundException(String.format("English Level not found for level id %s", id));
    }

    public static EnglishLevel fromDescription(String description) {
        if (description == null)
            return INTERMEDIATE;
        for (EnglishLevel el : EnglishLevel.values()) {
            if (el.label.toUpperCase().contains(description.toUpperCase())) {
                return el;
            }
        }
        return INTERMEDIATE;
    }

    public static List<EnglishLevelDTO> getAllLevels () {
        List<EnglishLevelDTO> levelDTOs = new ArrayList<>();
        List<EnglishLevel> englishLevels = Arrays.asList(EnglishLevel.values());
        englishLevels.stream()
                .sorted(Comparator.comparing(EnglishLevel::getLabel, String.CASE_INSENSITIVE_ORDER))
                .forEach(l -> {
                    EnglishLevelDTO levelDTO = new EnglishLevelDTO(l.getId(), l.getLabel());
                    levelDTOs.add(levelDTO);
                });
        return levelDTOs;
    }

    public static Optional<EnglishLevelDTO> findLevelById (Integer levelId) {
        List<EnglishLevel> englishLevels = Arrays.asList(EnglishLevel.values());
        Optional<EnglishLevel> englishLevelOptional = englishLevels.stream()
                .filter(l -> l.getId().equals(levelId)).findFirst();
        if (englishLevelOptional.isPresent()){
            EnglishLevel englishLevel = englishLevelOptional.get();
            EnglishLevelDTO levelDTO = new EnglishLevelDTO(englishLevel.getId(), englishLevel.getLabel());
            return Optional.of(levelDTO);
        }
        return Optional.empty();
    }

    public List<EnglishLevel> getNextLevels() {
        List<EnglishLevel> nextLevels = new ArrayList<>();
        Integer currentId = this.id;
        Integer nextId = this.next;
        while (nextId != currentId) {
            EnglishLevel nextEnglishLevel = fromId(nextId);
            nextLevels.add(nextEnglishLevel);
            currentId = nextEnglishLevel.id;
            nextId = nextEnglishLevel.next;
        }
        return nextLevels;
    }
}


