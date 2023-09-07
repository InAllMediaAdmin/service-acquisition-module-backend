package com.iam.serviceacquisition.domain;

import com.iam.serviceacquisition.common.enums.*;
import com.iam.serviceacquisition.domain.talentresume.*;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.EMPTY_SET;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Talent implements Cloneable {

    private static final long SEARCH_DAYS_MIN = 15;
    private static final long SEARCH_DAYS_MAX = 30;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    private List<Technology> mainTechnologies = newArrayList();

    @ManyToMany
    private List<Technology> secondaryTechnologies = newArrayList();

    @ElementCollection
    @CollectionTable(name = "talent_roles", joinColumns = @JoinColumn(name = "talent_id"))
    @Column(name = "default_role_id")
    Set<Long> roles = new HashSet<>();

    @Column(name = "seniority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TalentLevel level;

    @Column(name = "english_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnglishLevel englishLevel;

    @Column(name = "avatar")
    @Enumerated(EnumType.STRING)
    private Avatar avatar;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;

    private String city;

    private LocalDate availabilityDate;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "printable_name")
    private String printableName;

    @Column(name = "rate_per_hour", precision = 5, scale = 2)
    private BigDecimal rate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "worked_with_us")
    private int yearsWorkingWithUs;

    @Access(AccessType.PROPERTY)
    private double experience;

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private List<TalentLanguageCertification> languageCertifications = new ArrayList<>();

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private Set<TalentExperience> experiences = new HashSet<>();

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private List<TalentEducation> educations = new ArrayList<>();

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private List<TalentCertification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private List<TalentSoftSkill> softSkills = new ArrayList<>();

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private List<TalentTechnicalProfiles> technicalProfiles = new ArrayList<>();

    @OneToMany(mappedBy = "talent")
    @Builder.Default
    private List<TalentHighlight> highlight = new ArrayList<>();

    @Column(name = "email")
    private String email;

    @Column(name = "profile_linkedin")
    private String profileLinkedin;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TalentState state = TalentState.REAL;

    @Transient
    private Integer calculatedMatching;

    @Transient
    private LocalDate upgradeAvailabilityDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "email_iam")
    private String emailIam;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "profile_description")
    private String profileDescription;

    @Column(name = "interest_description")
    private String interestDescription;

    @Column(name = "gpt_ia")
    private boolean gptIA;

    @Column(name = "service_center_id")
    private Long serviceCenterId;

    @Column(name = "financial_info_id")
    private Long financialInfoId;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null && lastUpdate != null) {
            createdAt = lastUpdate;
        } else if (createdAt == null) {
            createdAt = LocalDateTime.now();
            lastUpdate = LocalDateTime.now();
        }
    }

    public boolean isTalentAdHoc() {
        return state == TalentState.ADHOC || state == TalentState.CLONE;
    }

    public void addTalentExperience(TalentExperience talentExperience) {
        experiences.add(talentExperience);
    }

    public void removeTalentExperience(TalentExperience talentExperience) {
        experiences.remove(talentExperience);
    }

    public void addTalentEducation(TalentEducation talentEducation) {
        educations.add(talentEducation);
    }

    public void removeTalentEducation(TalentEducation talentEducation) {
        educations.remove(talentEducation);
    }

    public void addTalentCertification(TalentCertification talentCertification) {
        certifications.add(talentCertification);
    }

    public void removeTalentCertification(TalentCertification talentCertification) {
        certifications.remove(talentCertification);
    }

    public void addTalentSkillSoft(TalentSoftSkill talentSoftSkill) {
        softSkills.add(talentSoftSkill);
    }

    public void removeTalentSkillSoft(TalentSoftSkill talentSoftSkill) {
        softSkills.remove(talentSoftSkill);
    }

    public void addLanguageCert(TalentLanguageCertification talentLanguageCertification) {
        languageCertifications.add(talentLanguageCertification);
    }

    public void removeLanguageCert(TalentLanguageCertification talentLanguageCertification) {
        languageCertifications.remove(talentLanguageCertification);
    }

    public void removeTalentTechnicalProfile(TalentTechnicalProfiles talentTechnicalProfiles) {
        technicalProfiles.remove(talentTechnicalProfiles);
    }

    public boolean hasNoExperience() {
        return experiences.size() == 0;
    }

    public boolean isNotReal() {
        return this.state != TalentState.REAL;
    }

    public double getExperience() {
        if (this.state == TalentState.ADHOC) {
            return this.experience;
        }
        if (experiences == null) {
            experiences = new HashSet<>();
        }
        return experiences.stream().mapToDouble(TalentExperience::getExperience).sum();
    }

    /* Deep copy of a talent instance */
    public Talent clone() {
        return this.toBuilder().id(0)
                .languageCertifications(isEmpty(languageCertifications) ? EMPTY_LIST : newArrayList(languageCertifications))
                .experiences(isEmpty(experiences) ? EMPTY_SET : newHashSet(experiences))
                .certifications(isEmpty(certifications) ? EMPTY_LIST : newArrayList(certifications))
                .secondaryTechnologies(isEmpty(secondaryTechnologies) ? EMPTY_LIST : newArrayList(secondaryTechnologies))
                //    .talentRoles(isEmpty(talentRoles) ? EMPTY_LIST : newArrayList(talentRoles))
                .mainTechnologies(isEmpty(mainTechnologies) ? EMPTY_LIST : newArrayList(mainTechnologies))
                .educations(isEmpty(educations) ? EMPTY_LIST : newArrayList(educations))
                .softSkills(isEmpty(softSkills) ? EMPTY_LIST : newArrayList(softSkills))
                .roles(isEmpty(roles) ? new HashSet<>() : new HashSet<>(roles))
                .highlight(isEmpty(highlight) ? EMPTY_LIST : newArrayList(highlight))
                .technicalProfiles(isEmpty(technicalProfiles) ? EMPTY_LIST : newArrayList(technicalProfiles))
                .build();
    }

    public void upgradeAvailabilityDate(LocalDate startDate) {
        if (isNull(availabilityDate)) {
            // if the search date is less than 15 days in the future.
            if (startDate.isBefore(LocalDate.now().plusDays(SEARCH_DAYS_MIN))) {
                // ready 2 weeks later
                availabilityDate = startDate.plusWeeks(2);
            }
            // else if the requested start date is at least 30 days
            else if (startDate.isAfter(LocalDate.now().plusDays(SEARCH_DAYS_MAX - 1))) {
                // ready on start date
                availabilityDate = startDate;
            }
            // start date is at least 15 days in the future but not beyond 30 days in the future.
            else {
                // ready a week later
                availabilityDate = startDate.plusWeeks(1);
            }
        }
    }

    public String countryTimezone() {
        return timeZone == null ? null : timeZone.getCountry();
    }


    public boolean hasCurrentlyIamExperience() {
        return experiences != null && experiences.stream()
                .anyMatch(experience -> Boolean.TRUE.equals(experience.getIamExperience())
                        && experience.getStartDate().isBefore(LocalDate.now())
                        && (experience.getEndDate() == null || experience.getEndDate().isAfter(LocalDate.now())));
    }


    public int getMonthsOfWorkingInIAM() {
        return experiences.stream()
                .filter(e -> e.getIamExperience() != null && e.getIamExperience())
                .mapToInt(experience -> {
                    LocalDate endDate = (experience.getEndDate() == null || experience.getEndDate().isAfter(LocalDate.now())) ? LocalDate.now() : experience.getEndDate();
                    int monthsBetween = (int) ChronoUnit.MONTHS.between(experience.getStartDate(), endDate);
                    if (experience.getStartDate().getDayOfMonth() != endDate.getDayOfMonth())
                        monthsBetween++;
                    return monthsBetween;
                })
                .sum();
    }

    public Optional<Industry> getMainIndustryExperience() {
        if (isNull(experiences)){
            return Optional.empty();
        }
        return experiences.stream()
                .map(talentExperience -> {
                    LocalDate endDate = talentExperience.getEndDate() != null ? talentExperience.getEndDate() : LocalDate.now();
                    long duration = ChronoUnit.DAYS.between(talentExperience.getStartDate(), endDate);
                    return new AbstractMap.SimpleEntry<>(talentExperience.getIndustry(), duration);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        Long::sum))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }

    public Technology getMainTechnology() {
        Map<Technology, Long> technologyDurationMap = new HashMap<>();

        if (experiences == null || experiences.isEmpty()) {
            return null;
        }

        for (TalentExperience experience : experiences) {
            LocalDate endDate = experience.getEndDate() != null ? experience.getEndDate() : LocalDate.now();
            long duration = ChronoUnit.DAYS.between(experience.getStartDate(), endDate);

            technologyDurationMap.putIfAbsent(experience.getMainTechnology(), 0L);
            technologyDurationMap.computeIfPresent(experience.getMainTechnology(), (tech, oldValue) -> oldValue + duration);
        }

        return technologyDurationMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public boolean isCreatedInServiceManager() {
        return serviceCenterId != null;
    }

}
