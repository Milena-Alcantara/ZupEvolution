package edu.zupevolution.service;

import edu.zupevolution.DTO.ProfessionalProfileRequestDTO;
import edu.zupevolution.model.HardSkillsModel;
import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.HardSkillsRepository;
import edu.zupevolution.repository.ProfessionalProfileRepository;
import edu.zupevolution.repository.UserRepository;
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
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private HardSkillsRepository hardSkillsRepository;

    public ResponseEntity<Object> createProfessionalProfile(@RequestBody ProfessionalProfileRequestDTO requestDTO) {
        if (requestDTO.getId_user() != null) {
            UserModel userModel = userRepository.findById(requestDTO.getId_user()).orElse(null);
            if (userModel != null) {
                if (!professionalRepository.existsById(requestDTO.getId_user())){
                    ProfessionalProfileModel profileModel = new ProfessionalProfileModel();
                    profileModel.setUserModel(userModel);
                    professionalRepository.save(profileModel);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Perfil Profissional do usuário criado com sucesso.");
                }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe perfil profissional para este usuário.");
                }

            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("É necessário associar um usuário existente ao seu perfil profissional.");
    }

    public ResponseEntity<Object> getUsersWithSkill(@Param("skillName") String skillName){
        if (skillName != null){
           Optional<List<Object[]>> usersWithSkill = Optional.ofNullable(professionalRepository.getUsersWithSkill(skillName));
           List<Object[]> list = usersWithSkill.get();
            if (!list.isEmpty())
                return ResponseEntity.status(HttpStatus.OK).body(usersWithSkill.get());
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi localizado nenhum usuário com a Skill" +
                        " solicitada.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("É necessário informar uma skill válida");
    }

    public ResponseEntity<Object> getAllProfessionalProfiles() {
        List<ProfessionalProfileModel> profiles = professionalRepository.findAll();
        if (!profiles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(profiles);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum perfil profissional encontrado.");
        }
    }
    public ResponseEntity<Object> updateProfessionalProfile(Long id, ProfessionalProfileModel updatedProfile) {
        Optional<ProfessionalProfileModel> existingProfileOptional = professionalRepository.findById(id);

        if (existingProfileOptional.isPresent()) {
            ProfessionalProfileModel existingProfile = existingProfileOptional.get();
            for (HardSkillsModel hardSkill : updatedProfile.getHardSkills()) {
                if (!findByCertificate(hardSkill.getCertificate())){
                    hardSkillsRepository.save(hardSkill);
                    existingProfile.setSoftSkills(updatedProfile.getSoftSkills());
                    existingProfile.setDescription(updatedProfile.getDescription());
                    existingProfile.setStrongPoints(updatedProfile.getStrongPoints());
                    existingProfile.setImprovementPoints(updatedProfile.getImprovementPoints());
                    existingProfile.setHardSkills(updatedProfile.getHardSkills());

                    professionalRepository.save(existingProfile);
                    return ResponseEntity.status(HttpStatus.OK).body("Perfil profissional atualizado com sucesso.");
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse certificado já esta inserido neste perfil.");
            }

    }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil profissional não encontrado.");

    }

    public Boolean findByCertificate(String certificate){
        Optional<HardSkillsModel> profileModel = hardSkillsRepository.findByCertificate(certificate);
        return profileModel.isPresent();
    }



}
