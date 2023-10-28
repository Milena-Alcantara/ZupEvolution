package edu.zupevolution.service;
import edu.zupevolution.model.TimelineModel;
import edu.zupevolution.repository.TimelineRepository;
import edu.zupevolution.util.DayOfWeekEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TimelineServiceTest {
    @Mock
    private TimelineRepository timelineRepository;
    @InjectMocks
    private TimelineService timelineService;
    private TimelineModel timelineModelOne;
    private DayOfWeekEnum dayOfWeekEnum;
    private TimelineModel timelineModelTwo;
    LocalTime time1 = LocalTime.of(10, 30);
    LocalTime time2 = LocalTime.of(20, 40);
    private List<LocalTime> studyTimes;
    private List<TimelineModel> timelineModels;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dayOfWeekEnum = DayOfWeekEnum.SEGUNDA;

        studyTimes = new ArrayList<>();
        studyTimes.add(time1);
        timelineModelOne = new TimelineModel(1L, dayOfWeekEnum, studyTimes);
        timelineModelTwo = new TimelineModel(2L, dayOfWeekEnum, studyTimes);

        timelineModels = new ArrayList<>();
        timelineModels.add(timelineModelOne);
        timelineModels.add(timelineModelTwo);
    }

    @Test
    @DisplayName("Deve retornar um lista que não seja vazia ao passar cronogramas com horários iguais.")
    public void testOneIsTimelineConflict() {
        when(timelineRepository.findByDayOfWeek(dayOfWeekEnum)).thenReturn(timelineModels);

        List<TimelineModel> conflicts = timelineService.isTimelineConflict(timelineModelOne);
        Assertions.assertFalse(conflicts.isEmpty());
    }
    @Test
    @DisplayName("Deve retornar um lista que não seja vazia ao passar cronogramas com horários iguais.")
    public void testTwoIsTimelineConflict() {
        timelineModels.clear();
        studyTimes.clear();
        studyTimes.add(time2);
        timelineModelTwo.setStudyTimes(studyTimes);
        when(timelineRepository.findByDayOfWeek(dayOfWeekEnum)).thenReturn(timelineModels);

        List<TimelineModel> conflicts = timelineService.isTimelineConflict(timelineModelOne);
        Assertions.assertTrue(conflicts.isEmpty());
    }
}
