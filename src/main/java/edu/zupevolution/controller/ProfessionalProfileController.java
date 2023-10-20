package edu.zupevolution.controller;

import edu.zupevolution.model.ProfessionalProfileModel;
import edu.zupevolution.service.ProfessionalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/professionalprofile")
public class ProfessionalProfileController {
    @Autowired
    private ProfessionalProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity<Object> createProfessionalProfile(@RequestBody ProfessionalProfileModel profileModel){
        return profileService.createProfessionalProfile(profileModel);
    }

    @GetMapping("/gelall")
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
