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
@Table(name = "soft_skills")
public class HardSkillsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn (name = "id_professional_profile", referencedColumnName = "id", nullable = false)
    private ProfessionalProfileModel professionalProfile;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "certificate_url", nullable = false)
    private List<String>certificate;
    @Column(name = "desired_courses", nullable = false)
    private List<String>desiredCourses;
}
