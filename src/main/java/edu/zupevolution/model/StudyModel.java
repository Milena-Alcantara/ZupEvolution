package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study")
public class StudyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @ElementCollection
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "study_skills_id"))
    private List<String>skills;

    @Column(name = "goal", nullable = false)
    private String goal;

    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "id_timeline", referencedColumnName = "id")
    private TimelineModel timeline;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserModel userModel;
}
