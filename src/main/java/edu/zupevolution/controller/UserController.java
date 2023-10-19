package edu.zupevolution.controller;

import edu.zupevolution.model.AccessTypeModel;
import edu.zupevolution.model.UserModel;
import edu.zupevolution.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zupevolution/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserModel userModel){
        return userService.createUser(userModel);
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable String email){
        return userService.deleteUser(email);
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<Object> findUserByEmail(@PathVariable String email){
        return userService.findUserByEmail(email);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllStudies() {
        return userService.getAllStudies();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateAccessTypeUserByID(@PathVariable Long id, @RequestBody AccessTypeModel accessTypeModel){
        return userService.updateAccessTypeUserByID(id,accessTypeModel);
    }
}
