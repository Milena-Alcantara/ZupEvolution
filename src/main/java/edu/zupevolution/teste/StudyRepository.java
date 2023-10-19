package edu.zupevolution.teste;

import edu.zupevolution.model.StudyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<StudyModel, Long> {
}