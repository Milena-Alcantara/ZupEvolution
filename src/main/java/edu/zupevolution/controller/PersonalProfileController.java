package edu.zupevolution.controller;

import edu.zupevolution.model.UserModel;
import edu.zupevolution.service.PersonalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/personalprofile")
public class PersonalProfileController {
    @Autowired
    private PersonalProfileService personalProfileService;
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProfessionalProfile(@PathVariable Long id, @RequestBody UserModel updatedUserModel) {
        return personalProfileService.updatePersonalProfile(id, updatedUserModel);
    }
    @GetMapping("/getAll/{id}")
    public ResponseEntity<Object> getProfessionalProfile(@PathVariable Long id) {
        return personalProfileService.getPersonalProfile(id);
    }
}
