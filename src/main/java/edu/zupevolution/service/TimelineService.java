package edu.zupevolution.service;

import edu.zupevolution.util.DayOfWeekEnum;
import edu.zupevolution.model.TimelineModel;
import edu.zupevolution.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimelineService {
    private final TimelineRepository timelineRepository;

    @Autowired
    public TimelineService(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }


    public List<TimelineModel> isTimelineConflict(TimelineModel newTimeline) {
        DayOfWeekEnum dayOfWeek = newTimeline.getDayOfWeek();
        List<LocalTime> studyTimes = newTimeline.getStudyTimes();

        List<TimelineModel> timelinesWithSameDay = timelineRepository.findByDayOfWeek(dayOfWeek);
        List<TimelineModel> conflicts = new ArrayList<>();

        for (TimelineModel existingTimeline : timelinesWithSameDay) {
            List<LocalTime> existingStudyTimes = existingTimeline.getStudyTimes();
            if (existingStudyTimes.stream().anyMatch(studyTimes::contains)) {
                conflicts.add(existingTimeline);
            }
        }
        return conflicts;
    }
}


