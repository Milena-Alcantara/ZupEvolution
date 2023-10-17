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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserModel userModel;

    @ElementCollection
    @CollectionTable(name = "soft_skills", joinColumns = @JoinColumn(name = "professional_profile_id"))
    private List<String> softSkills;

    @ManyToMany
    @JoinTable(
            name = "profile_hard_skills",
            joinColumns = @JoinColumn(name = "id_profile"),
            inverseJoinColumns = @JoinColumn(name = "id_skill")
    )
    private List<HardSkillsModel> hardSkills;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "strong_points", joinColumns = @JoinColumn(name = "professional_profile_id"))
    private List<String> strongPoints;

    @ElementCollection
    @CollectionTable(name = "improvement_points", joinColumns = @JoinColumn(name = "professional_profile_id"))
    private List<String> improvementPoints;

}
