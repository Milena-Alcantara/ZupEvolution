package edu.zupevolution.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
public class TimelineModel {

    private Map<DayOfWeek, List<LocalTime[]>> schedule;

    public TimelineModel() {
        schedule = new HashMap<>();
    }

}
