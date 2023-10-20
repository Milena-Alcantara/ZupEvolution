package edu.zupevolution.service;

import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.ProfessionalProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

}
