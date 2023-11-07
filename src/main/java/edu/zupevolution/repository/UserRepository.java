package edu.zupevolution.repository;

import edu.zupevolution.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE users.email = :email")
    Optional<UserModel> findByEmail(@Param("email") String email);


}
