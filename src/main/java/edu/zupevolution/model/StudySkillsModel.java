package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_skills")
public class StudySkillsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_hard_skills", referencedColumnName = "id", nullable = false)
    private HardSkillsModel hardSkills;
    @ManyToOne
    @JoinColumn(name = "id_study", referencedColumnName = "id", nullable = false)
    private StudyModel study;
}
