package edu.zupevolution.repository;

import edu.zupevolution.model.ProfessionalProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfileModel,Long> {
}
