package edu.zupevolution.service;

import edu.zupevolution.model.UserModel;
import edu.zupevolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> createUser(@RequestBody UserModel userModel){
        if (!dataValidate(userModel)) {
            userRepository.save(userModel);
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Verifique os dados informados.");
        }
       return ResponseEntity.status(HttpStatus.CREATED).body("Usuário salvo com sucesso.");
    }

    private boolean dataValidate(UserModel userModel) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        if (userModel.getName() == null || userModel.getBirthday() == null || userModel.getPassword().length() < 8 ||
                userModel.getPassword().isEmpty() || !userModel.getEmail().matches(String.valueOf(pattern))) {
            return false;
        }
        return true;
    }
}
