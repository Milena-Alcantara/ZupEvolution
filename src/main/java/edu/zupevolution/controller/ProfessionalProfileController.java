package edu.zupevolution.controller;

import edu.zupevolution.DTO.ProfessionalProfileRequestDTO;
import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.service.ProfessionalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/professionalprofile")
public class ProfessionalProfileController {
    @Autowired
    private ProfessionalProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity<Object> createProfessionalProfile(@RequestBody ProfessionalProfileRequestDTO requestDTO){
        return profileService.createProfessionalProfile(requestDTO);
    }
    @GetMapping("/getAllUsersForSkill/{skillName}")
    public ResponseEntity<Object> getUsersWithSkill(@PathVariable String skillName){
        return profileService.getUsersWithSkill(skillName);
    }

    @GetMapping("/getall")
    public ResponseEntity<Object> getProfessionalProfile() {
        return profileService.getAllProfessionalProfiles();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProfessionalProfile(@PathVariable Long id, @RequestBody ProfessionalProfileModel updatedProfileModel) {
        return profileService.updateProfessionalProfile(id, updatedProfileModel);
    }

    @PutMapping("/updatehardskil")
    public ResponseEntity<Object> updateHardSkillName(@RequestBody ProfessionalProfileModel updatedProfileModel, @RequestBody String profileHardSkillName, @RequestBody String newHardSkillName) {
        return profileService.updateHardSkillName(updatedProfileModel, profileHardSkillName, newHardSkillName);
    }
}
