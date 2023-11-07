package edu.zupevolution.model;

import edu.zupevolution.util.DayOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimelineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeekEnum dayOfWeek;

    @ElementCollection
    @CollectionTable(name = "study_times", joinColumns = @JoinColumn(name = "timeline_id"))
    @Column(name = "study_time")
    private List<LocalTime> studyTimes;
}
