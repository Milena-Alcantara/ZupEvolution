package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Professional_Profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalProfileModel {
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserModel userModel;
    @Column(name = "soft_skills")
    private List<String> softSkills;
    @OneToMany(mappedBy = "hard_skills")
    private List<HardSkillsModel> hardSkills;
    @Column(name = "description")
    private String description;
    @Column(name = "strong_points")
    private List<String> strongPoints;
    @Column(name = "improvement_points")
    private List<String> improvementPoints;

}
