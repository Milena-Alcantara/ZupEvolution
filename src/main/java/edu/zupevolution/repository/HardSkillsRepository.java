package edu.zupevolution.repository;

import edu.zupevolution.model.HardSkillsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HardSkillsRepository extends JpaRepository<HardSkillsModel,Long> {
    Optional<HardSkillsModel> findByCertificate(String certificate);
}
