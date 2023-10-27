package edu.zupevolution.repository;

import edu.zupevolution.model.StudyModel;
import edu.zupevolution.model.TimelineModel;
import edu.zupevolution.util.DayOfWeekEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<StudyModel, Long> {
    @Query(nativeQuery = true, value = " SELECT * FROM study_skills WHERE id_user = :id_user")
    List<StudyModel> findAllStudies(@Param("id_user") Long id_user);

    @Modifying
    @Query(value = "DELETE FROM skills WHERE study_skills_id = :studyId", nativeQuery = true)
    void deleteSkillsByStudyId(@Param("studyId") Long studyId);

    List<StudyModel> findByTimeline(TimelineModel timelineModel);

}