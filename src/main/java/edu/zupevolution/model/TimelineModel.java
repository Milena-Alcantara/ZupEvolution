package edu.zupevolution.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimelineModel {

    private Map<String, List<String>> schedule;

    public TimelineModel() {
        schedule = new HashMap<>();
    }

    public boolean addStudyTime(String dayOfWeek, String time) {
        if (schedule.containsKey(dayOfWeek)) {
            List<String> times = schedule.get(dayOfWeek);
            if (!times.contains(time)) {
                times.add(time);
                return true;
            } else {
                return false;
            }
        } else {
            List<String> times = new ArrayList<>();
            times.add(time);
            schedule.put(dayOfWeek, times);
            return true;
        }
    }
}
