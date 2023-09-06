package com.iam.serviceacquisition.domain.talentresume;

import com.iam.serviceacquisition.common.enums.Avatar;
import com.iam.serviceacquisition.common.enums.EnglishLevel;
import com.iam.serviceacquisition.common.enums.TalentLevel;
import com.iam.serviceacquisition.common.enums.TalentResumeState;
import com.iam.serviceacquisition.domain.TalentLanguageCertification;
import com.iam.serviceacquisition.domain.TeamPositionSlot;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TalentResume {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "adhoc")
    private Boolean adhoc;

    @Column(name = "real_name")
    private String realName;

    private String name;

    @Column(name = "avatar")
    @Enumerated(EnumType.STRING)
    private Avatar avatar;

    private String role;

    private Long defaultRoleId;

    @OneToOne(mappedBy = "talentResume")
    private TeamPositionSlot teamPositionSlot;

    @Column(name = "seniority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TalentLevel level;

    @Column(name = "english_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnglishLevel englishLevel;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private TalentResumeState status;

    @ManyToMany
    private List<TalentLanguageCertification> languageCertifications = new ArrayList<>();

    @ManyToMany
    private List<TalentExperience> experiences = new ArrayList<>();

    @ManyToMany
    private List<TalentIndustryExpertise>  industryExpertises = new ArrayList<>();

    @ManyToMany
    private List<TalentEducation> educations = new ArrayList<>();

    @ManyToMany
    private List<TalentCertification> certifications = new ArrayList<>();

    @ManyToMany
    private List<TalentSoftSkill> softSkills = new ArrayList<>();

    @Column(name = "profile_summary")
    private String profileSummary;

    private String interests;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "highlight_id")
    private TalentResumeHighlight resumeHighlight;

    private LocalDate availabilityDate;

    public void addExperience(TalentExperience experience) {
        this.experiences.add(experience);
    }

    public void addIndustryExpertise(TalentIndustryExpertise industryExpertise) {
        this.industryExpertises.add(industryExpertise);
    }

    public void addEducation(TalentEducation education) {
        this.educations.add(education);
    }

    public void addCertification(TalentCertification certification) {
        this.certifications.add(certification);
    }

    public void addSoftSkill(TalentSoftSkill softSkill) {
        this.softSkills.add(softSkill);
    }

    public void addLanguageCertification(TalentLanguageCertification languageCertification) {
        this.languageCertifications.add(languageCertification);
    }

    public void setTeamPositionSlot(TeamPositionSlot teamPositionSlot){
        if (teamPositionSlot == null) {
            if (this.teamPositionSlot != null) {
                this.teamPositionSlot.setTalentResume(null);
            }
        }
        else {
            teamPositionSlot.setTalentResume(this);
        }
        this.teamPositionSlot = teamPositionSlot;
    }
}
