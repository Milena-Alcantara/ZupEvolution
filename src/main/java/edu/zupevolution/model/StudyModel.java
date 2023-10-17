package edu.zupevolution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_skills")
public class StudyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "study_skills_id"))
    private List<String>skills;

    @Column(name = "goal", nullable = false)
    private String goal;

    @Column(name = "timeline", nullable = false)
    @Transient
    private TimelineModel timeline;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
