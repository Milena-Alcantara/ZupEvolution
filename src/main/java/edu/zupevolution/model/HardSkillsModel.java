package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hard_skills")
public class HardSkillsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "id_professional_profile", referencedColumnName = "id")
    private ProfessionalProfileModel professionalProfile;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "certificates_urls", joinColumns = @JoinColumn(name = "hard_skills_id"))
    private List<String>certificate;

    @ElementCollection
    @CollectionTable(name = "desired_courses", joinColumns = @JoinColumn(name = "hard_skills_id"))
    private List<String>desiredCourses;
}
