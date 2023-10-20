package edu.zupevolution.controller;

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
    public ResponseEntity<Object> createProfessionalProfile(@RequestBody ProfessionalProfileModel profileModel){
        return profileService.createProfessionalProfile(profileModel);
    }
    @GetMapping("/getAllUsersForSkill")
    public ResponseEntity<Object> getUsersWithSkill(@Param("skillName") String skillName){
        return profileService.getUsersWithSkill(skillName);
    }

}
