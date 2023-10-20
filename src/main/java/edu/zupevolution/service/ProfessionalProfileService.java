package edu.zupevolution.service;

import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.ProfessionalProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalProfileService {
    @Autowired
   private ProfessionalProfileRepository professionalRepository;
    public ResponseEntity<Object> createProfessionalProfile(@RequestBody ProfessionalProfileModel profileModel){
        if (profileModel.getUserModel() != null){
            professionalRepository.save(profileModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Perfil Profional do usuário criado com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("É necessário associar um usuário ao seu perfil profissional.");
    }
    public ResponseEntity<Object> getUsersWithSkill(@Param("skillName") String skillName){
        if (skillName != null){
           Optional<List<Object[]>> usersWithSkill = Optional.ofNullable(professionalRepository.getUsersWithSkill(skillName));
            if (usersWithSkill.isPresent())
                return ResponseEntity.status(HttpStatus.OK).body(usersWithSkill.get());
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi localizado nenhum usuário com a Skill" +
                        " solicitada.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("É necessário informar uma skill válida");
    }

}
