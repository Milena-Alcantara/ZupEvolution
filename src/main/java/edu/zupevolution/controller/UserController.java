package edu.zupevolution.controller;

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
}
