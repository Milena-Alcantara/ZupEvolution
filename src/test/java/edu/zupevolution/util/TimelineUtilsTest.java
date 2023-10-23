package edu.zupevolution.util;

import edu.zupevolution.model.TimelineModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimelineUtilsTest {

    private TimelineModel timelineModel;
    private TimelineUtils timelineUtils;

    @BeforeEach
    void setUp() {
        timelineModel = new TimelineModel();
        timelineUtils = new TimelineUtils(timelineModel);
        timelineUtils.translateDayOfWeek();
    }

    @Test
    @DisplayName("deve adicionar horário de estudo válido")
    void testAddStudyTimeValid() {
        assertTrue(timelineUtils.addStudyTime("Segunda", LocalTime.of(9, 0), LocalTime.of(10, 0)));
    }

    @Test
    @DisplayName("Teste Adicionar Tempo de Estudo Inválido")
    void testAddStudyTimeInvalid() {
        String dayOfWeek = "Terça";
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(13, 0);

        assertFalse(timelineUtils.addStudyTime(dayOfWeek, startTime, endTime));
    }

    @Test
    @DisplayName("Teste Verificar Timeline Válida")
    void testIsTimelineValid() {
        TimelineModel timelineModel = new TimelineModel();
        Map<DayOfWeek, List<LocalTime[]>> schedule = timelineModel.getSchedule();
        schedule.clear();

        schedule.put(DayOfWeek.MONDAY, new ArrayList<>(Arrays.asList(
                new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(11, 0)},
                new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(16, 0)}
        )));

        timelineModel.setSchedule(schedule);

        assertTrue(timelineUtils.isTimelineValid(timelineModel));
    }

    @Test
    @DisplayName("Teste Adicionar Horário e Verificar Conflitos")
    void testAddStudyTimeAndCheckConflicts() {
        TimelineModel timelineModel = new TimelineModel();
        TimelineUtils timelineUtils = new TimelineUtils(timelineModel);

        assertTrue(timelineUtils.addStudyTime("Segunda", LocalTime.of(9, 0), LocalTime.of(10, 0)));

        assertFalse(timelineUtils.addStudyTime("Segunda", LocalTime.of(9, 30), LocalTime.of(10, 30)));

        assertTrue(timelineUtils.addStudyTime("Segunda", LocalTime.of(10, 30), LocalTime.of(11, 0)));
    }


    @Test
    @DisplayName("Teste Adicionar Tempo de Estudo com Dia Inválido")
    void testAddStudyTimeWithInvalidDay() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            timelineUtils.addStudyTime("InvalidDay", LocalTime.of(9, 0), LocalTime.of(10, 0));
        });

        assertEquals("No enum constant java.time.DayOfWeek.InvalidDay", exception.getMessage());
    }


    @Test
    @DisplayName("Teste Verificar Timeline Inválida")
    void testIsTimelineInvalid() {
        TimelineModel timelineModel = new TimelineModel();
        TimelineUtils timelineUtils = new TimelineUtils(timelineModel);

        timelineModel.getSchedule().put(DayOfWeek.TUESDAY, new ArrayList<>(Arrays.asList(
                new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(10, 0)},
                new LocalTime[]{LocalTime.of(9, 30), LocalTime.of(10, 30)}
        )));
        assertFalse(timelineUtils.isTimelineValid(timelineModel));
    }
}