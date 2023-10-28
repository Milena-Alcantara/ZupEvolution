package edu.zupevolution.repository;

import edu.zupevolution.model.ProfessionalProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfileModel,Long> {
    @Query(nativeQuery = true, value =
            "SELECT u.id, u.name, u.email, pp.description " +
                    "FROM users AS u " +
                    "INNER JOIN professional_profile AS pp ON u.id = pp.id_user " +
                    "LEFT JOIN profile_hard_skills AS phs ON pp.id = phs.id_profile " +
                    "LEFT JOIN hard_skills AS hs ON phs.id_skill = hs.id " +
                    "LEFT JOIN soft_skills AS ss ON pp.id = ss.professional_profile_id " +
                    "WHERE hs.name = :skillName OR ss.soft_skills = :skillName ")
    List<Object[]> getUsersWithSkill(@Param("skillName") String skillName);
}
