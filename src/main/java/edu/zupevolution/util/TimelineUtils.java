package edu.zupevolution.util;

import edu.zupevolution.model.TimelineModel;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TimelineUtils {

    private TimelineModel timelineModel;
    private Map<String, DayOfWeek> dayOfWeekMap;


    public TimelineUtils(TimelineModel timelineModel) {
        this.timelineModel = timelineModel;
        dayOfWeekMap = new HashMap<>();
        translateDayOfWeek();
    }
    public boolean addStudyTime(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        DayOfWeek day = dayOfWeekMap.get(dayOfWeek);

        if (day == null) {
            if(dayOfWeekMap.containsValue(DayOfWeek.valueOf(dayOfWeek))){
                day = DayOfWeek.valueOf(String.valueOf(dayOfWeek));
            }
        }


        if (!isTimeValid(startTime, endTime)) {
            return false;
        }

        if (timelineModel.getSchedule().containsKey(day)) {
            List<LocalTime[]> times = timelineModel.getSchedule().get(day);

            for (LocalTime[] existingPeriod : times) {
                if (startTime.isBefore(existingPeriod[1]) && existingPeriod[0].isBefore(endTime)) {
                    return false;
                }
            }

            times.add(new LocalTime[]{startTime, endTime});
        } else {
            List<LocalTime[]> times = new ArrayList<>();
            times.add(new LocalTime[]{startTime, endTime});
            timelineModel.getSchedule().put(day, times);
        }

        return true;
    }

    private boolean isTimeValid(LocalTime startTime, LocalTime endTime) {
        return !startTime.isAfter(endTime);
    }


    public void translateDayOfWeek() {
        dayOfWeekMap = new HashMap<>();
        dayOfWeekMap.put("Domingo", DayOfWeek.SUNDAY);
        dayOfWeekMap.put("Segunda", DayOfWeek.MONDAY);
        dayOfWeekMap.put("Terça", DayOfWeek.TUESDAY);
        dayOfWeekMap.put("Quarta", DayOfWeek.WEDNESDAY);
        dayOfWeekMap.put("Quinta", DayOfWeek.THURSDAY);
        dayOfWeekMap.put("Sexta", DayOfWeek.FRIDAY);
        dayOfWeekMap.put("Sábado", DayOfWeek.SATURDAY);
    }

    public boolean isTimelineValid(TimelineModel timeline) {
        Map<DayOfWeek, List<LocalTime[]>> schedule = timeline.getSchedule();
        for (DayOfWeek key : schedule.keySet()) {
            List<LocalTime[]> values = schedule.get(key);
            if (key != null && values != null && !values.isEmpty()) {
                for (LocalTime[] timeArray : values) {
                    for (LocalTime time : timeArray) {
                        LocalTime startTime = timeArray[0];
                        LocalTime endTime = timeArray[1];
                        boolean result = addStudyTime(String.valueOf(key), startTime, endTime);
                        if (result) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

