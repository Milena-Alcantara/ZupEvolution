package edu.zupevolution.service;

import edu.zupevolution.model.HardSkillsModel;
import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.repository.HardSkillsRepository;
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
   @Autowired
   private HardSkillsRepository hardSkillsRepository;
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

            existingProfile.setSoftSkills(updatedProfile.getSoftSkills());
            existingProfile.setDescription(updatedProfile.getDescription());
            existingProfile.setStrongPoints(updatedProfile.getStrongPoints());
            existingProfile.setImprovementPoints(updatedProfile.getImprovementPoints());

            professionalRepository.save(existingProfile);
            return ResponseEntity.status(HttpStatus.OK).body("Perfil profissional atualizado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil profissional não encontrado.");
        }
    }

    public ResponseEntity<Object> updateHardSkillName(ProfessionalProfileModel professionalProfile, String profileHardSkillName, String newHardSkillName) {
        if (validateAndUpdateHardSkill(profileHardSkillName, newHardSkillName)) {
            List<HardSkillsModel> hardSkills = professionalProfile.getHardSkills();
            for (HardSkillsModel hardSkill : hardSkills) {
                if (hardSkill.getName().equals(profileHardSkillName)) {
                    hardSkill.setName(newHardSkillName);
                    HardSkillsModel updatedHardSkill = hardSkillsRepository.save(hardSkill);
                    return ResponseEntity.status(HttpStatus.OK).body(updatedHardSkill);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habilidade não encontrada.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nomes de habilidade inválidos.");
        }
    }

    public boolean validateAndUpdateHardSkill(String profileHardSkillName, String newHardSkillName) {
        return profileHardSkillName != null && newHardSkillName != null;
    }
}
