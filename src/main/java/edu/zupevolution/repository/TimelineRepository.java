package edu.zupevolution.repository;

import edu.zupevolution.util.DayOfWeekEnum;
import edu.zupevolution.model.TimelineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineRepository extends JpaRepository<TimelineModel, Long> {

    List<TimelineModel> findByDayOfWeek(DayOfWeekEnum dayOfWeek);
}
